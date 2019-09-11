package com.taobao.taobaoadmin.controller.Sms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.model.SmsHomeBrand;
import com.taobao.taobaoadmin.model.SmsHomeNewProduct;
import com.taobao.taobaoadmin.service.Sms.SmsHomeBrandService;
import com.taobao.taobaoadmin.service.Sms.SmsHomeNewProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页新品管理Controller
 */
@Controller
@Api(value = "SmsHomeNewProductController", description = "首页新品管理")
@RequestMapping("/home/newProduct")
public class SmsHomeNewProductController {

    @Autowired
    private SmsHomeNewProductService homeNewProductService;

    @ApiOperation("添加首页推荐品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestBody List<SmsHomeNewProduct> homeNewProductList) {
        int count = homeNewProductService.create(homeNewProductList);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("分页查询推荐")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(value = "productName",required = false)String productName,
                       @RequestParam(value = "recommendStatus",required = false)Integer recommendStatus,
                       @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum) {
        List<SmsHomeNewProduct> homeNewProductList = homeNewProductService.list(productName, recommendStatus, pageNum, pageSize);

        return new CommonResult().pageSuccess(homeNewProductList);
    }


    @ApiOperation("修改推荐排序")
    @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@PathVariable Long id,Integer sort) {
        int count = homeNewProductService.updateSort(id, sort);
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
        int count = homeNewProductService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("批量删除推荐")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestParam("ids")List<Long>ids) {
        int count = homeNewProductService.delete(ids);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

}
