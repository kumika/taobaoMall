package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.mapper.SmsHomeRecommendProductMapper;
import com.taobao.taobaoadmin.mapper.SmsHomeRecommendSubjectMapper;
import com.taobao.taobaoadmin.model.SmsHomeRecommendProduct;
import com.taobao.taobaoadmin.model.SmsHomeRecommendProductExample;
import com.taobao.taobaoadmin.model.SmsHomeRecommendSubject;
import com.taobao.taobaoadmin.model.SmsHomeRecommendSubjectExample;
import com.taobao.taobaoadmin.service.Sms.SmsHomeRecommendProductService;
import com.taobao.taobaoadmin.service.Sms.SmsHomeRecommendSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;


@Service
public class SmsHomeRecommendSubjectServiceIMPL implements SmsHomeRecommendSubjectService {

    @Autowired
    private SmsHomeRecommendSubjectMapper recommendSubjectMapper;

    @Override
    public int create(List<SmsHomeRecommendSubject> homeRecommendSubjectList) {

        for (SmsHomeRecommendSubject recommendSubject : homeRecommendSubjectList) {
            recommendSubject.setSort(0);
            recommendSubject.setRecommendStatus(1);
            recommendSubjectMapper.insert(recommendSubject);
        }

        return homeRecommendSubjectList.size();
    }

    @Override
    public int delete(List<Long> ids) {
        SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
        example.createCriteria().andIdIn(ids);
        return recommendSubjectMapper.deleteByExample(example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        SmsHomeRecommendSubject subject = new SmsHomeRecommendSubject();
        subject.setRecommendStatus(recommendStatus);

        SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
        example.createCriteria().andIdIn(ids);
        return recommendSubjectMapper.updateByExampleSelective(subject, example);
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeRecommendSubject subject = new SmsHomeRecommendSubject();
        subject.setId(id);
        subject.setSort(sort);

        return recommendSubjectMapper.updateByPrimaryKey(subject);
    }

    @Override
    public List<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);


        SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
        SmsHomeRecommendSubjectExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(subjectName)) {
            criteria.andSubjectNameLike("%" + subjectName + "%");
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        example.setOrderByClause("sort desc");
        return recommendSubjectMapper.selectByExample(example);
    }
}
