package com.taobao.taobaoadmin.controller.Cms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.model.CmsPrefrenceArea;
import com.taobao.taobaoadmin.service.Cms.CmsPreferenceAreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品优选管理Controller
 */
@Controller
@Api(tags = "CmsPrefrenceAreaController", description = "商品优选管理")
@RequestMapping("/prefrenceArea")
public class CmsPreferenceAreaController {

    @Autowired
    private CmsPreferenceAreService preferenceAreService;

    @ApiOperation("获取所有商品优选")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public Object listAll() {

        List<CmsPrefrenceArea> preferenceAreaList = preferenceAreService.listAll();
        return new CommonResult().success(preferenceAreaList);
    }
}
