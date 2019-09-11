package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.taobao.taobaoadmin.dto.Sms.SmsFlashPromotionSessionDetail;
import com.taobao.taobaoadmin.mapper.SmsFlashPromotionSessionMapper;
import com.taobao.taobaoadmin.model.SmsFlashPromotionSession;
import com.taobao.taobaoadmin.model.SmsFlashPromotionSessionExample;
import com.taobao.taobaoadmin.service.Sms.SmsFlashPromotionProductRelationService;
import com.taobao.taobaoadmin.service.Sms.SmsFlashPromotionSessionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SmsFlashPromotionSessionServiceIMPL implements SmsFlashPromotionSessionService {

    @Autowired
    private SmsFlashPromotionSessionMapper promotionSessionMapper;

    @Autowired
    private SmsFlashPromotionProductRelationService relationService;


    @Override
    public List<SmsFlashPromotionSession> list() {
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();

        return promotionSessionMapper.selectByExample(example);
    }

    @Override
    public List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId) {
        //根据输入参数id，使用example查询,
        //而且应该是和前端沟通好的，查询的时候只查询status = 1 范围里的id
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.createCriteria().andStatusEqualTo(1);
        List<SmsFlashPromotionSession> list = promotionSessionMapper.selectByExample(example);
        //因为客户要求页面上显示商品的数量
        //返回的限时购场次列表对象list，需要放到一个自定义的包含商品数量的场次信息的列表对象上
        //这个列表对象泛型是SmsFlashPromotionSessionDetail类，具有商品数量的属性，继承自SmsFlashPromotionSession类
        List<SmsFlashPromotionSessionDetail> result = new ArrayList<>();
        for (SmsFlashPromotionSession promotionSession : list) {
            SmsFlashPromotionSessionDetail detail = new SmsFlashPromotionSessionDetail();
            BeanUtils.copyProperties(promotionSession, detail);
            //查询商品的数量，使用限时购商品关联管理接口的方法来计算
            //此方法是根据活动id和场次id获取商品关系数量
            int count = relationService.getCount(flashPromotionId, promotionSession.getId());
            detail.setProductCount(count);
            result.add(detail);
        }
        return result;
    }

    @Override
    public int create(SmsFlashPromotionSession promotionSession) {
        promotionSession.setCreateTime(new Date());
        return promotionSessionMapper.insert(promotionSession);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotionSession promotionSession = new SmsFlashPromotionSession();
        promotionSession.setId(id);
        promotionSession.setStatus(status);
        return promotionSessionMapper.updateByPrimaryKeySelective(promotionSession);
    }

    @Override
    public int update(Long id, SmsFlashPromotionSession promotionSession) {
        promotionSession.setId(id);
        return promotionSessionMapper.updateByPrimaryKey(promotionSession);
    }

    @Override
    public int delete(Long id) {
        return promotionSessionMapper.deleteByPrimaryKey(id);
    }
}
