package com.taobao.taobaoadmin.controller.Sms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.dto.Sms.SmsFlashPromotionProduct;
import com.taobao.taobaoadmin.model.SmsFlashPromotionProductRelation;
import com.taobao.taobaoadmin.service.Sms.SmsFlashPromotionProductRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(value = "SmsFlashPromotionProductRelationController", description = "限时购和商品关系管理")
@RequestMapping("/flashProductRelation")
public class SmsFlashPromotionProductRelationController {

    @Autowired
    private SmsFlashPromotionProductRelationService relationService;

    @ApiOperation("分页查询不同场次关联及商品信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam("flashPromotionId") Long flashPromotionId,
                       @RequestParam("flashPromotionSessionId") Long flashPromotionSessionId,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsFlashPromotionProduct> flashPromotionProductList = relationService.list(flashPromotionId, flashPromotionSessionId, pageSize, pageNum);
        return new CommonResult().pageSuccess(flashPromotionProductList);
    }


    @ApiOperation("修改关联相关信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, @RequestBody SmsFlashPromotionProductRelation relation) {
        int count = relationService.update(id, relation);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("批量选择商品添加关联")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestBody List<SmsFlashPromotionProductRelation> relationList) {
        int count = relationService.create(relationList);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("删除关联")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        int count = relationService.delete(id);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
}
