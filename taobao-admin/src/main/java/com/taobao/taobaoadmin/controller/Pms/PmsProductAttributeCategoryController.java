package com.taobao.taobaoadmin.controller.Pms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.dto.Pms.PmsProductAttributeCategoryItem;
import com.taobao.taobaoadmin.model.PmsProductAttributeCategory;
import com.taobao.taobaoadmin.service.Pms.PmsProductAttributeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性分类
 */
@Controller
@Api(tags = "PmsProductAttributeCategoryController", description = "商品属性分类管理")
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService productAttributeCategoryService;


    @ApiOperation("分页获取所有商品属性分类")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @ResponseBody
    public Object getList(@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) {
        List<PmsProductAttributeCategory> productAttributeCategoryList = productAttributeCategoryService.getList(pageSize, pageNum);
        return new CommonResult().pageSuccess(productAttributeCategoryList);
    }

    @ApiOperation("获取所有商品属性分类及其下属性")
    @RequestMapping(value = "/list/withAttr", method = RequestMethod.GET)
    @ResponseBody
    public Object getListWithAttr() {
        List<PmsProductAttributeCategoryItem> productAttributeCategoryResultList = productAttributeCategoryService.getListWithAttr();
        return new CommonResult().success(productAttributeCategoryResultList);
    }

    @ApiOperation("修改商品属性分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, @RequestParam String name) {
        int count = productAttributeCategoryService.update(id, name);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("增加商品属性分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestParam String name) {
        int count = productAttributeCategoryService.create(name);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @ApiOperation("删除单个商品属性分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        int count = productAttributeCategoryService.delete(id);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
