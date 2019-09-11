package com.taobao.taobaoadmin.service.IMPL.Pms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dao.CMS.CmsPrefrenceAreaProductRelationDao;
import com.taobao.taobaoadmin.dao.CMS.CmsSubjectProductRelationDao;
import com.taobao.taobaoadmin.dao.pms.*;
import com.taobao.taobaoadmin.dto.Pms.PmsProductParam;
import com.taobao.taobaoadmin.dto.Pms.PmsProductQueryParam;
import com.taobao.taobaoadmin.dto.Pms.PmsProductResult;
import com.taobao.taobaoadmin.mapper.*;
import com.taobao.taobaoadmin.model.*;
import com.taobao.taobaoadmin.service.Pms.PmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 商品管理Service实现类
 */
@Service
public class PmsProductServiceImpL  implements PmsProductService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpL.class);

    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private PmsMemberPriceDao memberPriceDao;

    @Autowired
    private PmsMemberPriceMapper memberPriceMapper;


    @Autowired
    private PmsProductLadderDao productLadderDao;

    @Autowired
    private PmsProductLadderMapper productLadderMapper;

    @Autowired
    private PmsProductFullReductionDao productFullReductionDao;

    @Autowired
    private PmsProductFullReductionMapper productFullReductionMapper;


    @Autowired
    private PmsSkuStockDao skuStockDao;

    @Autowired
    private PmsSkuStockMapper skuStockMapper;

    @Autowired
    private PmsProductAttributeValueDao productAttributeValueDao;

    @Autowired
    private PmsProductAttributeValueMapper productAttributeValueMapper;

    @Autowired
    private CmsSubjectProductRelationDao subjectProductRelationDao;

    @Autowired
    private CmsSubjectProductRelationMapper subjectProductRelationMapper;


    @Autowired
    private CmsPrefrenceAreaProductRelationDao prefrenceAreaProductRelationDao;

    @Autowired
    private CmsPrefrenceAreaProductRelationMapper prefrenceAreaProductRelationMapper;

    @Autowired
    private PmsProductDao productDao;

    @Autowired
    private PmsProductVertifyRecordDao productVertifyRecordDao;


    /**
     * 建立和插入关系表操作
     * @param dao  可以操作的dao
     * @param dataList  要插入的数据
     * @param productId  建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            if (CollectionUtils.isEmpty(dataList)) return;

            //这里是有规划的，属性列表dataList的主要内容是客户填写的，我们这里只是需要写属性是归类于哪个产品id就可以了
            //这个循环是为了给产品的属性列表中的属性赋予产品ID，表面这个属性是哪个产品哪个ID的。
            for (Object item : dataList) {
                //Method method = ownerClass.getMethod(methodName, argsClass)：
                // 通过methodName和参数的argsClass（方法中的参数类型集合）数组得到要执行的Method。
                // 返回一个方法对象
                Method setId = item.getClass().getMethod("setId", Long.class);
                //method.invoke(owner, args)：执行该Method.invoke方法的参数是执行这个方法的对象owner，和参数数组args，
                //可以这么理解：owner对象中带有参数args的method方法。返回值是Object，也既是该方法的返回值。
                //这里就是根据”setId“获取到XX对象的所有setId方法，当args=null，说明调用的是所有setId方法中的无参数值方法，
                setId.invoke(item, (Long)null);
                //获取XX对象的所有setProductId方法
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                //调用XX对象中的setProductId方法并且方法中参数值要带有productId，返回值是Object
                setProductId.invoke(item, productId);
            }
            //得到插入数据列表方法insertList
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            //dao对象mapper进行插入操作----就是以前写的这句mapper.insertList(XXXList)
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("创建产品出错：{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }


    /**
     * 处理sku的编码，在sku编码后面加入当前日期和商品id
     * @param skuStockList
     * @param productId
     */
    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if (CollectionUtils.isEmpty(skuStockList)) return;

        for (int i = 0; i < skuStockList.size();i++) {
            //获取sku的编码
            PmsSkuStock skuStock = skuStockList.get(i);
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                //对于处理时间短的服务或者启动频率高的要用单线程，相反用多线程
                //单线程操作字符串缓冲流同时有大量数据的时候，使用StringBuilder
                StringBuilder sd = new StringBuilder();
                //日期，获取当前日期
                sd.append(sdf.format(new Date()));
                //四位商品id，%04d是输出商品id不足4位数字则向左用0补齐4位数，d是10进制整型
                sd.append(String.format("%04d", productId));
                //3位索引id，%03d是输出索引id不足3位数字则向左用0补齐3位数，d是10进制整型
                sd.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sd.toString());
            }
        }
    }


    /**
     * 创建产品
     * @param productParam
     * @return
     */
    @Override
    public int create(PmsProductParam productParam) {
        int count;

        //创建商品
        PmsProduct product = productParam;
        product.setId(null);
        productMapper.insertSelective(product);

        //根据促销类型设置价格：、阶梯价格、满减价格
        Long productId = product.getId();

        //会员价格
        relateAndInsertList(memberPriceDao, productParam.getMemberPriceList(), productId);

        //阶梯价格
        relateAndInsertList(productLadderDao, productParam.getProductLadderList(), productId);

        //满减价格
        relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), productId);

        //处理sku的编码，在sku编码后面加入当前日期和商品id
        handleSkuStockCode(productParam.getSkuStockList(), productId);

        //添加sku库存信息
        relateAndInsertList(skuStockDao, productParam.getSkuStockList(), productId);

        //添加商品参数,添加自定义商品规格
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), productId);

        //关联专题
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), productId);

        //关联优选
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), productId);

        count = 1;
        return count;
    }

    @Override
    public List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
        //MyBatis分页插件
        PageHelper.startPage(pageNum, pageSize);

        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);

        //上架状态
        if (productQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        //审核状态
        if (productQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        //商品名称模糊关键字
        if (!StringUtils.isEmpty(productQueryParam.getKeyword())) {
            criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
        }

        //商品货号
        if (!StringUtils.isEmpty(productQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(productQueryParam.getProductSn());
        }

        //商品分类编号
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }

        //商品品牌编号
        if (productQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
        }


        return productMapper.selectByExample(productExample);
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return productDao.getUpdateInfo(id);
    }



    @Override
    public int update(Long id, PmsProductParam productParam) {
        int count;
        //更新商品信息
        PmsProduct product = productParam;
        product.setId(id);
        productMapper.updateByPrimaryKeySelective(product);
        //会员价格
        PmsMemberPriceExample pmsMemberPriceExample = new PmsMemberPriceExample();
        pmsMemberPriceExample.createCriteria().andProductIdEqualTo(id);
        //删除原来的会员数据，然后重新插入会员数据
        memberPriceMapper.deleteByExample(pmsMemberPriceExample);
        //插入会员数据
        relateAndInsertList(memberPriceDao, productParam.getMemberPriceList(), id);

        //阶梯价格
        PmsProductLadderExample ladderExample = new PmsProductLadderExample();
        ladderExample.createCriteria().andProductIdEqualTo(id);
        //删除原来的阶梯价格数据，然后重新插入阶梯价格数据
        productLadderMapper.deleteByExample(ladderExample);
        //插入
        relateAndInsertList(productLadderDao, productParam.getProductLadderList(), id);

        //满减价格
        PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
        fullReductionExample.createCriteria().andProductIdEqualTo(id);
        //删除
        productFullReductionMapper.deleteByExample(fullReductionExample);
        //插入
        relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), id);

        //修改sku库存信息
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(id);
        //删除
        skuStockMapper.deleteByExample(skuStockExample);
        //处理sku的编码
        handleSkuStockCode(productParam.getSkuStockList(), id);
        //插入
        relateAndInsertList(skuStockDao,productParam.getSkuStockList(),id);

        //修改商品参数，添加自定义商品规格
        PmsProductAttributeValueExample productAttributeValueExample = new PmsProductAttributeValueExample();
        productAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        //删除
        productAttributeValueMapper.deleteByExample(productAttributeValueExample);
        //插入
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), id);

        //关联专题
        CmsSubjectProductRelationExample subjectProductRelationExample = new CmsSubjectProductRelationExample();
        subjectProductRelationExample.createCriteria().andProductIdEqualTo(id);
        //删除
        subjectProductRelationMapper.deleteByExample(subjectProductRelationExample);
        //插入
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), id);


        //关联优选
        CmsPrefrenceAreaProductRelationExample prefrenceAreaExample = new CmsPrefrenceAreaProductRelationExample();
        prefrenceAreaExample.createCriteria().andProductIdEqualTo(id);
        //删除
        prefrenceAreaProductRelationMapper.deleteByExample(prefrenceAreaExample);
        //插入
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), id);

        count = 1;
        return count;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        PmsProduct record = new PmsProduct();
        record.setPublishStatus(publishStatus);
        PmsProductExample example = new PmsProductExample();
        //使用方法andIdIn()
        example.createCriteria().andIdIn(ids);
        //当有参数为null的时候，使用XXXByExampleSelective
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct record = new PmsProduct();
        record.setRecommandStatus(recommendStatus);
        PmsProductExample example = new PmsProductExample();
        //使用方法andIdIn()
        example.createCriteria().andIdIn(ids);
        //当有参数为null的时候，使用XXXByExampleSelective
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct record = new PmsProduct();
        record.setNewStatus(newStatus);
        PmsProductExample example = new PmsProductExample();
        //使用方法andIdIn()
        example.createCriteria().andIdIn(ids);
        //当有参数为null的时候，使用XXXByExampleSelective
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(verifyStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        List<PmsProductVertifyRecord> list = new ArrayList<>();

        int count = productMapper.updateByExampleSelective(product, example);
        //修改完审核状态后插入审核记录
        for (Long id : ids) {
            PmsProductVertifyRecord record = new PmsProductVertifyRecord();
            record.setProductId(id);
            record.setCreateTime(new Date());
            record.setDetail(detail);
            record.setStatus(verifyStatus);
            record.setVertifyMan("test");
            list.add(record);
        }
        productVertifyRecordDao.insertList(list);
        return count;
    }

    @Override
    public List<PmsProduct> list(String keyword) {
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%"+keyword+"%");
            productExample
                    .or()
                    .andDeleteStatusEqualTo(0)
                    .andProductSnLike("%" + keyword + "%");
        }
        return productMapper.selectByExample(productExample);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {

        PmsProduct record = new PmsProduct();
        record.setDeleteStatus(deleteStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record,example);
    }

}
