package com.taobao.taobaoadmin.service.IMPL.Oms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.mapper.OmsOrderReturnReasonMapper;
import com.taobao.taobaoadmin.model.OmsOrderReturnApplyExample;
import com.taobao.taobaoadmin.model.OmsOrderReturnReason;
import com.taobao.taobaoadmin.model.OmsOrderReturnReasonExample;
import com.taobao.taobaoadmin.service.Oms.OmsOrderReturnReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单原因管理Service实现类
 */
@Service
public class OmsOrderReturnReasonServiceIMPL implements OmsOrderReturnReasonService {

    @Autowired
    private OmsOrderReturnReasonMapper orderReturnReasonMapper;

    @Override
    public List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.setOrderByClause("sort desc");

        return orderReturnReasonMapper.selectByExample(example);
    }

    @Override
    public OmsOrderReturnReason getItem(Long id) {
        return orderReturnReasonMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, OmsOrderReturnReason returnReason) {
        returnReason.setId(id);
        return orderReturnReasonMapper.updateByPrimaryKey(returnReason);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        if (!status.equals(0) && !status.equals(1)) {
            return 0;
        }
        OmsOrderReturnReason record = new OmsOrderReturnReason();
        record.setStatus(status);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andIdIn(ids);
        return orderReturnReasonMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int create(OmsOrderReturnReason returnReason) {
        returnReason.setCreateTime(new Date());
        return orderReturnReasonMapper.insert(returnReason);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andIdIn(ids);
        return orderReturnReasonMapper.deleteByExample(example);
    }


}
