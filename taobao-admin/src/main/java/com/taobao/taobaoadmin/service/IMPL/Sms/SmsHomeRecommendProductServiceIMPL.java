package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.mapper.SmsHomeRecommendProductMapper;
import com.taobao.taobaoadmin.model.SmsHomeNewProduct;
import com.taobao.taobaoadmin.model.SmsHomeRecommendProduct;
import com.taobao.taobaoadmin.model.SmsHomeRecommendProductExample;
import com.taobao.taobaoadmin.service.Sms.SmsHomeNewProductService;
import com.taobao.taobaoadmin.service.Sms.SmsHomeRecommendProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;


@Service
public class SmsHomeRecommendProductServiceIMPL implements SmsHomeRecommendProductService {

    @Autowired
    private SmsHomeRecommendProductMapper recommendProductMapper;

    @Override
    public int create(List<SmsHomeRecommendProduct> homeRecommendProductList) {

        for (SmsHomeRecommendProduct recommendProduct : homeRecommendProductList) {
            recommendProduct.setSort(0);
            recommendProduct.setRecommendStatus(1);
            recommendProductMapper.insert(recommendProduct);
        }

        return homeRecommendProductList.size();
    }

    @Override
    public int delete(List<Long> ids) {
        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        example.createCriteria().andIdIn(ids);
        return recommendProductMapper.deleteByExample(example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        SmsHomeRecommendProduct product = new SmsHomeRecommendProduct();
        product.setRecommendStatus(recommendStatus);

        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        example.createCriteria().andIdIn(ids);
        return recommendProductMapper.updateByExampleSelective(product, example);
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeRecommendProduct product = new SmsHomeRecommendProduct();
        product.setId(id);
        product.setSort(sort);

        return recommendProductMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public List<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);


        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        SmsHomeRecommendProductExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(productName)) {
            criteria.andProductNameLike("%" + productName + "%");
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        example.setOrderByClause("sort desc");
        return recommendProductMapper.selectByExample(example);
    }
}
