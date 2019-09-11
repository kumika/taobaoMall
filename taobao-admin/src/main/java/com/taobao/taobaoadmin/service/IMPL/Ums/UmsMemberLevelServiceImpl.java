package com.taobao.taobaoadmin.service.IMPL.Ums;

import com.taobao.taobaoadmin.mapper.UmsMemberLevelMapper;
import com.taobao.taobaoadmin.model.UmsMemberLevel;
import com.taobao.taobaoadmin.model.UmsMemberLevelExample;
import com.taobao.taobaoadmin.service.Ums.UmsMemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService {

    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;

    @Override
    public List<UmsMemberLevel> list(Integer defaultStatus) {

        UmsMemberLevelExample example = new UmsMemberLevelExample();
        example.createCriteria().andDefaultStatusEqualTo(defaultStatus);
        return memberLevelMapper.selectByExample(example);
    }
}
