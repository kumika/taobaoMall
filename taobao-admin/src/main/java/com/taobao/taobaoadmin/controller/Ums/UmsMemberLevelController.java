package com.taobao.taobaoadmin.controller.Ums;


import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.model.UmsMemberLevel;
import com.taobao.taobaoadmin.service.Ums.UmsMemberLevelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Api(tags = "UmsMemberLevelController", description = "会员等级管理")
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {

    @Autowired
    private UmsMemberLevelService memberLevelService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam("defaultStatus") Integer defaultStatus) {
        List<UmsMemberLevel> memberLevelList = memberLevelService.list(defaultStatus);

        return new CommonResult().pageSuccess(memberLevelList);
    }
}
