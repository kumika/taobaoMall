package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.mapper.SmsFlashPromotionMapper;
import com.taobao.taobaoadmin.model.SmsFlashPromotion;
import com.taobao.taobaoadmin.model.SmsFlashPromotionExample;
import com.taobao.taobaoadmin.service.Sms.SmsFlashPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class SmsFlashPromotionServiceIMPL implements SmsFlashPromotionService {

    @Autowired
    private SmsFlashPromotionMapper flashPromotionMapper;

    @Override
    public List<SmsFlashPromotion> list(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andTitleLike("%" + keyword + "%");
        }
        return flashPromotionMapper.selectByExample(example);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotion flashPromotion = new SmsFlashPromotion();
        flashPromotion.setId(id);
        flashPromotion.setStatus(status);

        return flashPromotionMapper.updateByPrimaryKeySelective(flashPromotion);
    }

    @Override
    public int update(Long id, SmsFlashPromotion flashPromotion) {
        flashPromotion.setId(id);
        return flashPromotionMapper.updateByPrimaryKeySelective(flashPromotion);
    }

    @Override
    public int create(SmsFlashPromotion flashPromotion) {
        flashPromotion.setCreateTime(new Date());
        return flashPromotionMapper.insert(flashPromotion);
    }

    @Override
    public int delete(Long id) {
        return flashPromotionMapper.deleteByPrimaryKey(id);
    }
}
