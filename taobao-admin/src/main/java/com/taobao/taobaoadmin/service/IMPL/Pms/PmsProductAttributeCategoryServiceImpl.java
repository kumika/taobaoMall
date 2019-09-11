package com.taobao.taobaoadmin.service.IMPL.Pms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dao.pms.PmsProductAttributeCategoryDao;
import com.taobao.taobaoadmin.dto.Pms.PmsProductAttributeCategoryItem;
import com.taobao.taobaoadmin.mapper.PmsProductAttributeCategoryMapper;
import com.taobao.taobaoadmin.model.PmsProductAttributeCategory;
import com.taobao.taobaoadmin.model.PmsProductAttributeCategoryExample;
import com.taobao.taobaoadmin.service.Pms.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PmsProductAttributeCategoryService的实现类
 */
@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Autowired
    private PmsProductAttributeCategoryDao productAttributeCategoryDao;

    @Override
    public List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);

        return productAttributeCategoryMapper.selectByExample(new PmsProductAttributeCategoryExample());
    }

    @Override
    public List<PmsProductAttributeCategoryItem> getListWithAttr() {
        return productAttributeCategoryDao.getListWithAttr();
    }

    @Override
    public int update(Long id, String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategoryItem();
        //把输入的参数，赋值给新对象，然后更新这个对象到数据库中
        productAttributeCategory.setName(name);
        productAttributeCategory.setId(id);

        return productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategory);
    }

    @Override
    public int create(String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
        productAttributeCategory.setName(name);

        return productAttributeCategoryMapper.insertSelective(productAttributeCategory);
    }

    @Override
    public int delete(Long id) {
        return productAttributeCategoryMapper.deleteByPrimaryKey(id);
    }
}
