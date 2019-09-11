package com.taobao.taobaoadmin.service.IMPL.Cms;

import com.taobao.taobaoadmin.mapper.CmsPrefrenceAreaMapper;
import com.taobao.taobaoadmin.model.CmsPrefrenceArea;
import com.taobao.taobaoadmin.model.CmsPrefrenceAreaExample;
import com.taobao.taobaoadmin.service.Cms.CmsPreferenceAreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品优选Service实现类
 */
@Service
public class CmsPreferenceAreServiceImpl implements CmsPreferenceAreService {

    @Autowired
    private CmsPrefrenceAreaMapper preferenceAreaMapper;

    @Override
    public List<CmsPrefrenceArea> listAll() {
        return preferenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
    }
}
