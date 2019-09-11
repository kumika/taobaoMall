package com.taobao.taobaoadmin.controller.Oms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.model.OmsOrderSetting;
import com.taobao.taobaoadmin.service.Oms.OmsOrderSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(value = "OmsOrderSettingController", description = "订单设置管理")
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {

    @Autowired
    private OmsOrderSettingService orderSettingService;


    @ApiOperation("获取指定订单设置")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@PathVariable Long id) {
        OmsOrderSetting orderSetting = orderSettingService.getItem(id);
        return new CommonResult().success(orderSetting);
    }

    @ApiOperation("修改指定订单设置")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, @RequestBody OmsOrderSetting orderSetting) {
        int count = orderSettingService.update(id, orderSetting);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }



}
