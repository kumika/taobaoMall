package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dao.Sms.SmsFlashPromotionProductRelationDao;
import com.taobao.taobaoadmin.dto.Sms.SmsFlashPromotionProduct;
import com.taobao.taobaoadmin.mapper.SmsFlashPromotionProductRelationMapper;
import com.taobao.taobaoadmin.model.SmsFlashPromotionProductRelation;
import com.taobao.taobaoadmin.model.SmsFlashPromotionProductRelationExample;
import com.taobao.taobaoadmin.service.Sms.SmsFlashPromotionProductRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsFlashPromotionProductRelationServiceIMPL implements SmsFlashPromotionProductRelationService {

    @Autowired
    private SmsFlashPromotionProductRelationMapper relationMapper;

    @Autowired
    private SmsFlashPromotionProductRelationDao relationDao;


    @Override
    public int getCount(Long flashPromotionId, Long flashPromotionSessionId) {
        SmsFlashPromotionProductRelationExample example = new SmsFlashPromotionProductRelationExample();
        example.createCriteria()
                .andFlashPromotionIdEqualTo(flashPromotionId)
                .andFlashPromotionSessionIdEqualTo(flashPromotionSessionId);
        return relationMapper.countByExample(example);
    }

    @Override
    public List<SmsFlashPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return relationDao.getList(flashPromotionId, flashPromotionSessionId);
    }

    @Override
    public int update(Long id, SmsFlashPromotionProductRelation relation) {
        relation.setId(id);
        return relationMapper.updateByPrimaryKeySelective(relation);
    }

    @Override
    public int create(List<SmsFlashPromotionProductRelation> relationList) {
        for (SmsFlashPromotionProductRelation promotionProductRelation : relationList) {
            relationMapper.insert(promotionProductRelation);
        }
        return relationList.size();
    }

    @Override
    public int delete(Long id) {
        return relationMapper.deleteByPrimaryKey(id);
    }
}
