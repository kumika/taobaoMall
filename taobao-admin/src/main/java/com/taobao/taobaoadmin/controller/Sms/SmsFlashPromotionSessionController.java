package com.taobao.taobaoadmin.controller.Sms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.dto.Sms.SmsFlashPromotionSessionDetail;
import com.taobao.taobaoadmin.model.SmsFlashPromotionSession;
import com.taobao.taobaoadmin.service.Sms.SmsFlashPromotionSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 限时购场次管理Controller
 */
@Controller
@Api(value = "SmsFlashPromotionSessionController", description = "限时购场次管理")
@RequestMapping("/flashSession")
public class SmsFlashPromotionSessionController {
    @Autowired
    private SmsFlashPromotionSessionService flashPromotionSessionService;


    @ApiOperation("获取全部场次")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        List<SmsFlashPromotionSession> promotionSessionList = flashPromotionSessionService.list();
        return new CommonResult().success(promotionSessionList);
    }


    @ApiOperation("获取全部可选场次及其数量")
    @RequestMapping(value = "/selectList", method = RequestMethod.GET)
    @ResponseBody
    public Object selectList(Long flashPromotionId) {
        List<SmsFlashPromotionSessionDetail> promotionSessionList = flashPromotionSessionService.selectList(flashPromotionId);
        return new CommonResult().success(promotionSessionList);
    }


    @ApiOperation("添加场次")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestBody SmsFlashPromotionSession promotionSession) {
        int count = flashPromotionSessionService.create(promotionSession);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("修改场次")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id,
                         @RequestBody SmsFlashPromotionSession promotionSession) {
        int count = flashPromotionSessionService.update(id, promotionSession);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("修改启用状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateStatus(@PathVariable Long id,Integer status) {
        int count = flashPromotionSessionService.updateStatus(id, status);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("删除场次")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        int count = flashPromotionSessionService.delete(id);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

}
