package com.taobao.taobaoadmin.service.IMPL.Pms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dto.Pms.PmsBrandParam;
import com.taobao.taobaoadmin.mapper.PmsBrandMapper;
import com.taobao.taobaoadmin.mapper.PmsProductMapper;
import com.taobao.taobaoadmin.model.PmsBrand;
import com.taobao.taobaoadmin.model.PmsBrandExample;
import com.taobao.taobaoadmin.model.PmsProduct;
import com.taobao.taobaoadmin.model.PmsProductExample;
import com.taobao.taobaoadmin.service.Pms.PmsBrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品品牌Service实现类
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService{

    @Autowired
    private PmsBrandMapper brandMapper;

    @Autowired
    private PmsProductMapper productMapper;

    @Override
    public List listBrand(String keyword, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria = pmsBrandExample.createCriteria();

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }

        return brandMapper.selectByExample(pmsBrandExample);
    }

    @Override
    public Object getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateBrand(Long id, PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam, pmsBrand);
        pmsBrand.setId(id);

        //如果创建时首字母为空，取名称的第一个为首字母
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0,1));
        }
        //更新品牌时要更新商品中的品牌名称
        PmsProduct product = new PmsProduct();
        product.setBrandName(pmsBrand.getName());
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andBrandIdEqualTo(id);
        productMapper.updateByExampleSelective(product, example);
        return brandMapper.updateByPrimaryKeySelective(pmsBrand);
    }

    @Override
    public int createBrand(PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam, pmsBrand);

        //如果创建时首字母为空，取名称的第一个为首字母
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0,1));
        }

        return brandMapper.insertSelective(pmsBrand);
    }

    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBrandBatch(List<Long> ids) {
        PmsBrandExample example = new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.deleteByExample(example);
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setFactoryStatus(factoryStatus);

        PmsBrandExample BrandExample = new PmsBrandExample();
        BrandExample.createCriteria().andIdIn(ids);
        return brandMapper.updateByExampleSelective(pmsBrand, BrandExample);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setShowStatus(showStatus);
        PmsBrandExample BrandExample = new PmsBrandExample();
        BrandExample.createCriteria().andIdIn(ids);
        return brandMapper.updateByExampleSelective(pmsBrand, BrandExample);
    }


}
