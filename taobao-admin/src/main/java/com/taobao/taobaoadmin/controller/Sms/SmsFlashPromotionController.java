package com.taobao.taobaoadmin.controller.Sms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.model.SmsFlashPromotion;
import com.taobao.taobaoadmin.service.Sms.SmsFlashPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 限时购活动管理Controller
 */
@Controller
@Api(value = "SmsFlashPromotionController", description = "限时购活动管理")
@RequestMapping("/flash")
public class SmsFlashPromotionController {

    @Autowired
    private SmsFlashPromotionService flashPromotionService;

    @ApiOperation("根据活动名称分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsFlashPromotion> flashPromotionList = flashPromotionService.list(keyword, pageNum, pageSize);

        return new CommonResult().pageSuccess(flashPromotionList);
    }


    @ApiOperation("修改上下线状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateStatus(@PathVariable Long id, Integer status) {
        int count = flashPromotionService.updateStatus(id, status);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("编辑活动信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, @RequestBody SmsFlashPromotion flashPromotion) {
        int count = flashPromotionService.update(id, flashPromotion);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("添加活动信息")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object update( @RequestBody SmsFlashPromotion flashPromotion) {
        int count = flashPromotionService.create(flashPromotion);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("删除活动信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        int count = flashPromotionService.delete(id);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
}
