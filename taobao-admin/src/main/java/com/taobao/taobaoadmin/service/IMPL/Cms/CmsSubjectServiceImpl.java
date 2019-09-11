package com.taobao.taobaoadmin.service.IMPL.Cms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.mapper.CmsSubjectMapper;
import com.taobao.taobaoadmin.model.CmsSubject;
import com.taobao.taobaoadmin.model.CmsSubjectExample;
import com.taobao.taobaoadmin.service.Cms.CmsSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * 商品专题Service实现类
 */
@Service
public class CmsSubjectServiceImpl implements CmsSubjectService {

    @Autowired
    private CmsSubjectMapper subjectMapper;

    @Override
    public List<CmsSubject> listAll() {
        return subjectMapper.selectByExample(new CmsSubjectExample());
    }

    @Override
    public List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        CmsSubjectExample example = new CmsSubjectExample();
        CmsSubjectExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTitleLike("%" + keyword + "%");
        }

        return subjectMapper.selectByExample(example);
    }
}
