package com.taobao.taobaoadmin.service.IMPL.Pms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dao.pms.PmsProductAttributeDao;
import com.taobao.taobaoadmin.dto.Pms.ProductAttrInfo;
import com.taobao.taobaoadmin.mapper.PmsProductAttributeMapper;
import com.taobao.taobaoadmin.model.PmsProductAttribute;
import com.taobao.taobaoadmin.model.PmsProductAttributeExample;
import com.taobao.taobaoadmin.service.Pms.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品属性Service实现类
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {

    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;

    @Autowired
    private PmsProductAttributeDao productAttributeDao;


    @Override
    public List<PmsProductAttribute> getList(Long cid,Integer type , Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
        return productAttributeMapper.selectByExample(example);
    }

    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        return productAttributeDao.getProductAttrInfo(productCategoryId);
    }
}
