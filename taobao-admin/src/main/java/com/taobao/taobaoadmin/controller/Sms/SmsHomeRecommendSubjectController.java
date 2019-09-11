package com.taobao.taobaoadmin.controller.Sms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.model.SmsHomeRecommendProduct;
import com.taobao.taobaoadmin.model.SmsHomeRecommendSubject;
import com.taobao.taobaoadmin.service.Sms.SmsHomeRecommendProductService;
import com.taobao.taobaoadmin.service.Sms.SmsHomeRecommendSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * 首页专题推荐管理Controller
 */
@Controller
@Api(value = "SmsHomeRecommendSubjectController", description = "首页专题推荐管理")
@RequestMapping("/home/recommendSubject")
public class SmsHomeRecommendSubjectController {

    @Autowired
    private SmsHomeRecommendSubjectService recommendSubjectService;

    @ApiOperation("添加首页推荐")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestBody List<SmsHomeRecommendSubject> homeRecommendSubjects) {
        int count = recommendSubjectService.create(homeRecommendSubjects);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("分页查询推荐")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(value = "subjectName",required = false)String subjectName,
                       @RequestParam(value = "recommendStatus",required = false)Integer recommendStatus,
                       @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum) {
        List<SmsHomeRecommendSubject> recommendSubjectList = recommendSubjectService.list(subjectName, recommendStatus, pageNum, pageSize);

        return new CommonResult().pageSuccess(recommendSubjectList);
    }


    @ApiOperation("修改推荐排序")
    @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id,Integer sort) {
        int count = recommendSubjectService.updateSort(id, sort);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("批量修改推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public Object updateRecommendStatus(@RequestParam("ids")List<Long>ids,
                                        @RequestParam Integer recommendStatus) {
        int count = recommendSubjectService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("批量删除推荐")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestParam("ids")List<Long>ids) {
        int count = recommendSubjectService.delete(ids);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

}
