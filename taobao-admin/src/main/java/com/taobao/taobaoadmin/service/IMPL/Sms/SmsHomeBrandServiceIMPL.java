package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.mapper.SmsHomeBrandMapper;
import com.taobao.taobaoadmin.model.SmsHomeBrand;
import com.taobao.taobaoadmin.model.SmsHomeBrandExample;
import com.taobao.taobaoadmin.service.Sms.SmsHomeBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * 首页品牌管理Service实现类
 */
@Service
public class SmsHomeBrandServiceIMPL implements SmsHomeBrandService {


    @Autowired
    private SmsHomeBrandMapper homeBrandMapper;

    @Override
    public int create(List<SmsHomeBrand> homeBrandList) {
        //这里对首页品牌设置的属性，应该是根据数据库表上的字段约束，
        // 或者属性设定来设置的
        for (SmsHomeBrand homeBrand : homeBrandList) {
            //推荐状态设置为1
            homeBrand.setRecommendStatus(1);
            //排序为0
            homeBrand.setSort(0);
            homeBrandMapper.insert(homeBrand);
        }
        return homeBrandList.size();
    }

    @Override
    public List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        PageHelper.offsetPage(pageNum, pageSize);

        SmsHomeBrandExample example = new SmsHomeBrandExample();
        SmsHomeBrandExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(brandName)) {
            criteria.andBrandNameLike("%" + brandName+"%");
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        example.setOrderByClause("sort desc");
        return homeBrandMapper.selectByExample(example);
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        //创建一个新SmsHomeBrand对象，把参数赋值进对象，进行更新
        SmsHomeBrand homeBrand = new SmsHomeBrand();
        homeBrand.setId(id);
        homeBrand.setSort(sort);

        return homeBrandMapper.updateByPrimaryKeySelective(homeBrand);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        //参数一个是对象的属性，一个是SQL语句的条件，的时候，使用XXXByExampleSelective
        SmsHomeBrand record = new SmsHomeBrand();
        record.setRecommendStatus(recommendStatus);

        SmsHomeBrandExample example = new SmsHomeBrandExample();
        example.createCriteria().andIdIn(ids);
        return homeBrandMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(List<Long> ids) {
        SmsHomeBrandExample example = new SmsHomeBrandExample();
        example.createCriteria().andIdIn(ids);
        return homeBrandMapper.deleteByExample(example);
    }
}
