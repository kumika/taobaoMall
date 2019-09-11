package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.github.pagehelper.PageHelper;

import com.taobao.taobaoadmin.mapper.SmsHomeNewProductMapper;
import com.taobao.taobaoadmin.model.SmsHomeNewProduct;
import com.taobao.taobaoadmin.model.SmsHomeNewProductExample;
import com.taobao.taobaoadmin.service.Sms.SmsHomeNewProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 首页新品推荐管理Service实现类
 */
@Service
public class SmsHomeNewProductServiceIMPL implements SmsHomeNewProductService {

    @Autowired
    private SmsHomeNewProductMapper newProductMapper;

    @Override
    public List<SmsHomeNewProduct> list(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);


        SmsHomeNewProductExample example = new SmsHomeNewProductExample();
        SmsHomeNewProductExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(productName)) {
            criteria.andProductNameLike("%" + productName + "%");
        }

        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        example.setOrderByClause("sort desc");
        return newProductMapper.selectByExample(example);
    }

    //首页推荐，是一个表，打包过来的商品列表自然是要一个个插入
    @Override
    public int create(List<SmsHomeNewProduct> homeNewProductList) {
        for (SmsHomeNewProduct product : homeNewProductList) {
            product.setRecommendStatus(1);
            product.setSort(0);
            newProductMapper.insert(product);
        }
        return homeNewProductList.size();
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeNewProduct product = new SmsHomeNewProduct();
        product.setSort(sort);
        product.setId(id);
        //更新对象的部分属性值，使用此方法。newProductMapper.updateByPrimaryKeySelective(product);
        return newProductMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        SmsHomeNewProduct product = new SmsHomeNewProduct();
        product.setRecommendStatus(recommendStatus);

        SmsHomeNewProductExample example = new SmsHomeNewProductExample();
        example.createCriteria().andIdIn(ids);
        return newProductMapper.updateByExampleSelective(product, example);
    }

    @Override
    public int delete(List<Long> ids) {
        SmsHomeNewProductExample example = new SmsHomeNewProductExample();
        example.createCriteria().andIdIn(ids);
        return newProductMapper.deleteByExample(example);
    }
}
