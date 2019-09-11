package com.taobao.taobaoadmin.service.IMPL.Oms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dao.Oms.OmsOrderReturnApplyDao;
import com.taobao.taobaoadmin.dto.Oms.OmsOrderReturnApplyResult;
import com.taobao.taobaoadmin.dto.Oms.OmsReturnApplyQueryParam;
import com.taobao.taobaoadmin.dto.Oms.OmsUpdateStatusParam;
import com.taobao.taobaoadmin.mapper.OmsOrderReturnApplyMapper;
import com.taobao.taobaoadmin.model.OmsOrderReturnApply;
import com.taobao.taobaoadmin.model.OmsOrderReturnApplyExample;
import com.taobao.taobaoadmin.service.Oms.OmsOrderReturnApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class OmsOrderReturnApplyServiceIMPL implements OmsOrderReturnApplyService {

    @Autowired
    private OmsOrderReturnApplyDao orderReturnApplyDao;


    @Autowired
    private OmsOrderReturnApplyMapper returnApplyMapper;

    @Override
    public List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return orderReturnApplyDao.getList(queryParam);
    }

    @Override
    public OmsOrderReturnApplyResult getItem(Long id) {
        return orderReturnApplyDao.getDetail(id);
    }

    @Override
    public int updateStatus(Long id, OmsUpdateStatusParam statusParam) {
        Integer status = statusParam.getStatus();
        OmsOrderReturnApply returnApply = new OmsOrderReturnApplyResult();
        if (status.equals(1)) {
            //确认退货
            returnApply.setId(id);
            returnApply.setStatus(1);
            returnApply.setReturnAmount(statusParam.getReturnAmount());
            returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        } else if (status.equals(2)) {
            //完成退货
            returnApply.setId(id);
            returnApply.setStatus(2);
            returnApply.setReceiveTime(new Date());
            returnApply.setReceiveMan(statusParam.getReceiveMan());
            returnApply.setReceiveNote(statusParam.getReceiveNote());
        } else if (status.equals(3)) {
            returnApply.setId(id);
            returnApply.setStatus(3);
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        } else {
            return 0;
        }
        return returnApplyMapper.updateByPrimaryKeySelective(returnApply);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnApplyExample example = new OmsOrderReturnApplyExample();
        example.createCriteria().andIdIn(ids).andStatusEqualTo(3);

        return returnApplyMapper.deleteByExample(example);
    }
}
