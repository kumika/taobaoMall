package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.mapper.SmsHomeAdvertiseMapper;
import com.taobao.taobaoadmin.model.SmsHomeAdvertise;
import com.taobao.taobaoadmin.model.SmsHomeAdvertiseExample;
import com.taobao.taobaoadmin.service.Sms.SmsHomeAdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SmsHomeAdvertiseServiceIMPL implements SmsHomeAdvertiseService{

    @Autowired
    private SmsHomeAdvertiseMapper advertiseMapper;

    @Override
    public List<SmsHomeAdvertise> list(String name, Integer type, String endTime, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
        SmsHomeAdvertiseExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (!StringUtils.isEmpty(endTime)) {
            //因为时间的格式不对，所以需要转换格式
            //输入的时间格式是：
            //SQL语句查询时间的格式（或者是数据库存储的时间格式）：
            String startStr = endTime + " 00:00:00";
            String endStr = endTime + " 23:59:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date start = null;
            try {
                start = sdf.parse(startStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date end = null;
            try {
                end = sdf.parse(endStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (start != null && end != null) {
                criteria.andEndTimeBetween(start, end);
            }
        }
        example.setOrderByClause("sort desc");
        return advertiseMapper.selectByExample(example);
    }

    @Override
    public SmsHomeAdvertise getItem(Long id) {
        return advertiseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsHomeAdvertise advertise = new SmsHomeAdvertise();
        advertise.setId(id);
        advertise.setStatus(status);
        return advertiseMapper.updateByPrimaryKeySelective(advertise);
    }

    @Override
    public int update(Long id, SmsHomeAdvertise advertise) {
        advertise.setId(id);
        return advertiseMapper.updateByPrimaryKeySelective(advertise);
    }

    @Override
    public int delete(List<Long> ids) {
        SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
        example.createCriteria().andIdIn(ids);
        return advertiseMapper.deleteByExample(example);
    }

    @Override
    public int create(SmsHomeAdvertise homeAdvertise) {
        homeAdvertise.setClickCount(0);
        homeAdvertise.setOrderCount(0);
        return advertiseMapper.insert(homeAdvertise);
    }
}
