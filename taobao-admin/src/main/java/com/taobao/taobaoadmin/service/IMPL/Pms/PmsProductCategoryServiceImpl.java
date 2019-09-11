package com.taobao.taobaoadmin.service.IMPL.Pms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dao.pms.PmsProductCategoryAttributeRelationDao;
import com.taobao.taobaoadmin.dao.pms.PmsProductCategoryDao;
import com.taobao.taobaoadmin.dto.Pms.*;
import com.taobao.taobaoadmin.mapper.PmsProductCategoryAttributeRelationMapper;
import com.taobao.taobaoadmin.mapper.PmsProductCategoryMapper;
import com.taobao.taobaoadmin.mapper.PmsProductMapper;
import com.taobao.taobaoadmin.model.*;
import com.taobao.taobaoadmin.service.Pms.PmsProductCategoryService;
import com.taobao.taobaoadmin.service.Pms.PmsProductService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * PmsProductCategoryService实现类
 */
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryDao productCategoryDao;

    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;

    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private PmsProductCategoryAttributeRelationMapper productCategoryAttributeRelationMapper;

    @Autowired
    private PmsProductCategoryAttributeRelationDao productCategoryAttributeRelationDao;


    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        return productCategoryDao.listWithChildren();
    }

    @Override
    public List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return productCategoryMapper.selectByExample(example);
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        PmsProductCategory productCategory = new PmsProductCategory();
        //更改分类对象的属性
        productCategory.setNavStatus(navStatus);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        //查询条件
        example.createCriteria().andIdIn(ids);
        //插入已经更改好的分类对象
        return productCategoryMapper.updateByExampleSelective(productCategory, example);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsProductCategory productCategory = new PmsProductCategory();
        //更改分类对象的属性
        productCategory.setShowStatus(showStatus);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        //查询条件
        example.createCriteria().andIdIn(ids);
        //插入已经更改好的分类对象
        return productCategoryMapper.updateByExampleSelective(productCategory, example);
    }

    @Override
    public PmsProductCategory getItem(Long id) {
        return productCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, PmsProductCategoryParam pmsproductCategoryParam) {

        //创建商品分类对象
        PmsProductCategory productCategory = new PmsProductCategory();
        //设置分类对象的id
        productCategory.setId(id);
        //传输请求参数到这个分类对象上
        BeanUtils.copyProperties(pmsproductCategoryParam,productCategory);
        //设置分类对象的分类级别
        setCategoryLevel(productCategory);

        //更新商品分类时更新商品中的分类名称
        //创建商品对象
        PmsProduct product = new PmsProduct();
        //从分类对象中获取商品名称，然后赋值给商品对象
        product.setProductCategoryName(productCategory.getName());
        //创建example，创建更新条件语句
        PmsProductExample example = new PmsProductExample();
        //根据传入的id，创建更新条件语句
        example.createCriteria().andProductCategoryIdEqualTo(id);
        //进行更新，参数：更新对象，更新条件
        productMapper.updateByExampleSelective(product, example);

        //同时更新筛选属性的信息，更新的是 分类与属性关系 的表格------就是页面上的客户写的筛选条件
        //获取传入参数中的属性列表，判断是否为空，不为空进入判断。
        if (!CollectionUtils.isEmpty(pmsproductCategoryParam.getProductAttributeIdList())) {
            //创建example，根据传入的id创建删除条件语句
            PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
            relationExample.createCriteria().andProductCategoryIdEqualTo(id);
            //执行删除属性
            productCategoryAttributeRelationMapper.deleteByExample(relationExample);
            //根据传入的id，再插入属性值
            insertRelationList(id, pmsproductCategoryParam.getProductAttributeIdList());
        } else {
            //获取传入参数中的属性列表，判断是否为空，为空，则进行删除属性
            PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
            //创建删除条件语句
            relationExample.createCriteria().andProductCategoryIdEqualTo(id);
            //执行语句
            productCategoryAttributeRelationMapper.deleteByExample(relationExample);
        }
        //最后更新商品分类
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    @Override
    public int delete(Long id) {
        return productCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int create(PmsProductCategoryParam pmsproductCategoryParam) {
        //创建商品分类对象
        PmsProductCategory productCategory = new PmsProductCategory();
        //分类对象设置分类级别，0是最高级别
        productCategory.setProductCount(0);
        //使用别的函数对分类对象传输数据
        BeanUtils.copyProperties(pmsproductCategoryParam,productCategory);

        //没有父分类时为一级分类
        setCategoryLevel(productCategory);
        int count = productCategoryMapper.insertSelective(productCategory);

        //创建筛选属性关联
        //获取属性列表
        List<Long> productAttributeIdList = pmsproductCategoryParam.getProductAttributeIdList();
        if (!CollectionUtils.isEmpty(productAttributeIdList)) {
            //对分类进行关联属性
            insertRelationList(productCategory.getId(), productAttributeIdList);
        }
        return count;
    }

    /**
     * 批量插入商品分类与筛选属性关系表
     *
     * @param productCategoryId      商品分类id
     * @param productAttributeIdList 相关商品筛选属性id集合
     */
    private void insertRelationList(Long productCategoryId, List<Long> productAttributeIdList) {
        //创建存放一个分类与属性关系类型的分类与属性关系列表对象
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
        //遍历商品筛选属性id集合
        for (Long productAttrId : productAttributeIdList) {
            //创建分类与属性关系类对象A
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            //遍历对象赋值到A对象上
            relation.setProductAttributeId(productAttrId);
            //产品分类id也赋值到对象A上
            relation.setProductCategoryId(productCategoryId);
            //A对象添加到分类与属性关系列表上
            relationList.add(relation);
        }
        //根据分类与属性关系列表进行更新
        productCategoryAttributeRelationDao.insertList(relationList);
    }

    /**
     * 根据分类的parentId设置分类的level
     */
    private void setCategoryLevel(PmsProductCategory productCategory) {
        //没有父分类时为一级分类
        if (productCategory.getParentId() == 0) {
            //设置分类等级为最高级
            productCategory.setLevel(0);
        } else {
            //有父分类时选择根据父分类level设置
            PmsProductCategory parentCategory = productCategoryMapper.selectByPrimaryKey(productCategory.getParentId());
            if (parentCategory != null) {
                //获取父类的分类等级，再此数值上+1，然后设置为自己的分类等级
                productCategory.setLevel(parentCategory.getLevel() + 1);
            } else {
                productCategory.setLevel(0);
            }
        }
    }
}
