package com.taobao.taobaoadmin.controller.Cms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.model.CmsSubject;
import com.taobao.taobaoadmin.service.Cms.CmsSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品专题Controller
 */
@Controller
@Api(tags = "CmsSubjectController",description = "商品专题管理" )
@RequestMapping("/subject")
public class CmsSubjectController {
    @Autowired
    private CmsSubjectService subjectService;

    @ApiOperation("获取全部商品专题")
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    @ResponseBody
    public Object listAll() {
        List<CmsSubject> subjectList = subjectService.listAll();
        return new CommonResult().success(subjectList);
    }

    @ApiOperation(value = "根据专题名称分页获取专题")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object getList(@RequestParam(value = "keyword",required=false) String keyword,
                          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize) {
        List<CmsSubject> subjectList = subjectService.list(keyword, pageNum, pageSize);
        return new CommonResult().pageSuccess(subjectList);
    }

}
