package com.taobao.taobaoadmin.service.IMPL.Oms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dao.Oms.OmsOrderDao;
import com.taobao.taobaoadmin.dao.Oms.OmsOrderOperateHistoryDao;
import com.taobao.taobaoadmin.dto.Oms.*;
import com.taobao.taobaoadmin.mapper.OmsOrderMapper;
import com.taobao.taobaoadmin.mapper.OmsOrderOperateHistoryMapper;
import com.taobao.taobaoadmin.model.OmsOrder;
import com.taobao.taobaoadmin.model.OmsOrderExample;
import com.taobao.taobaoadmin.model.OmsOrderOperateHistory;
import com.taobao.taobaoadmin.service.Oms.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单管理Service实现类
 */
@Service
public class OmsOrderServiceIMPL implements OmsOrderService {

    @Autowired
    private OmsOrderDao orderDao;

    @Autowired
    private OmsOrderMapper orderMapper;

    @Autowired
    private OmsOrderOperateHistoryDao orderOperateHistoryDao;

    @Autowired
    private OmsOrderOperateHistoryMapper orderOperateHistoryMapper;


    @Override
    public List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return orderDao.getList(queryParam);
    }

    @Override
    public OmsOrderDetail detail(Long id) {
        return orderDao.getDetail(id);
    }

    @Override
    public int delete(List<Long> ids) {
        //创建新订单对象
        OmsOrder record = new OmsOrder();
        record.setDeleteStatus(1);
        //设置查询条件
        OmsOrderExample example = new OmsOrderExample();
        //Mybatis的查询准则方法：andDeleteStatusEqualTo---查询属性deleteStatus等于0的对象，后面还可以加条件
        //andIdIn----查询id在指定集合中的对象，后面还可以加条件，但是这里就没有必要了。
        //criteria (评判或作决定的) 标准，准则，原则
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        //执行更新方法
        return orderMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delivery(List<OmsOrderDeliveryParam> deliveryParamList) {
        //批量发货
        int count = orderDao.delivery(deliveryParamList);

        //增加操作记录
        //这里有lambda 的应用
        //方法stream()---将集合转换为流， deliveryParamList.stream()就是把List转换成流
        //map(T -> R)----将流中的每一个元素 T 映射为 R（类似类型转换），这里将omsOrderDeliveryParam映射为history
        //就是将omsOrderDeliveryParam类型，映射成OmsOrderOperateHistory类型，omsOrderDeliveryParam是接收网页请求类，
        //这里假如不使用lambda，就单单是一个OmsOrderOperateHistory类对象的赋值过程
        //映射，不是转换，映射是XX对象的XX属性赋值给YY对象的YY属性
        //因为输入参数deliveryParamList是一个集合，每一个元素都要赋值执行，所以才使用lambda表达式的map()方法，进行遍历赋值
        List<OmsOrderOperateHistory> operateHistoryList = deliveryParamList.stream()
                .map(omsOrderDeliveryParam -> {
                    OmsOrderOperateHistory history = new OmsOrderOperateHistory();
                    history.setOrderId(omsOrderDeliveryParam.getOrderId());
                    history.setCreateTime(new Date());
                    history.setOperateMan("后台管理员");
                    history.setOrderStatus(2);
                    history.setNote("完成发货");
                    return history;
                }).collect(Collectors.toList());//collect 收集数据,它的方法toList()把流中所有元素收集到一个 List中
        System.out.println(operateHistoryList.toString());
        orderOperateHistoryDao.insertList(operateHistoryList);
        return count;
    }

    @Override
    public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);


        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setId(receiverInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);

        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(id);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息：" + note);
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(moneyInfoParam.getOrderId());
        order.setFreightAmount(moneyInfoParam.getFreightAmount());
        order.setDiscountAmount(moneyInfoParam.getDiscountAmount());
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);

        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(moneyInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(moneyInfoParam.getStatus());
        history.setNote("修改费用信息" );
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int close(List<Long> ids, String note) {
        OmsOrder record = new OmsOrder();
        record.setStatus(4);
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        int count = orderMapper.updateByExampleSelective(record, example);


        //删除操作记录
        List<OmsOrderOperateHistory> historyList = ids.stream().map(orderId -> {
            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderId(orderId);
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setOrderStatus(4);
            history.setNote("订单关闭："+note);
            return history;
        }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(historyList);
        return count;
    }


}
