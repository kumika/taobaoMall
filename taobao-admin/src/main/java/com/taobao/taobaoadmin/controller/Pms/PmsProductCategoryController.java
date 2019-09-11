package com.taobao.taobaoadmin.controller.Pms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.dto.Pms.PmsProductCategoryParam;
import com.taobao.taobaoadmin.dto.Pms.PmsProductCategoryWithChildrenItem;
import com.taobao.taobaoadmin.model.PmsProductCategory;
import com.taobao.taobaoadmin.service.Pms.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类模块Controller
 */
@Controller
@Api(tags = "PmsProductCategoryController", description = "商品分类管理")
@RequestMapping("/productCategory")
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService productCategoryService;


    @ApiOperation("查询所有一级分类及子分类")
    @RequestMapping(value = "/list/withChildren",method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:productCategory:read')")
    public Object listWithChildren() {

        List<PmsProductCategoryWithChildrenItem> list = productCategoryService.listWithChildren();
        return new CommonResult().success(list);
    }

    //PathVariable是绑定URI模板变量值，就是用来获得请求url中的动态参数的===》/list/{parentId}
    @ApiOperation("分页查询商品分类")
    @RequestMapping(value = "/list/{parentId}",method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:productCategory:read')")
    public Object getList(@PathVariable Long parentId,
                          @RequestParam(value = "pageSize",defaultValue="5") Integer pageSize,
                          @RequestParam(value = "pageNum",defaultValue="1") Integer pageNum) {
        List<PmsProductCategory> productCategoryList = productCategoryService.getList(parentId, pageSize, pageNum);
        return new CommonResult().pageSuccess(productCategoryList);
    }


    @ApiOperation("修改导航栏显示状态")
    @RequestMapping(value = "/update/navStatus",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:productCategory:update')")
    public Object updateNavStatus(@RequestParam("ids") List<Long> ids,
                                  @RequestParam("navStatus") Integer navStatus) {
        int count = productCategoryService.updateNavStatus(ids, navStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("修改显示状态")
    @RequestMapping(value = "/update/showStatus",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:productCategory:update')")
    public Object updateShowStatus(@RequestParam("ids") List<Long> ids,
                                  @RequestParam("showStatus") Integer showStatus) {
        int count = productCategoryService.updateShowStatus(ids, showStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("根据id获取商品分类")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:productCategory:read')")
    public Object getItem(@PathVariable Long id) {
        PmsProductCategory productCategory = productCategoryService.getItem(id);
        return new CommonResult().success(productCategory);
    }


    @ApiOperation("更新/修改商品分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:productCategory:update')")
    public Object update(@PathVariable Long id,
                         @Validated
                         @RequestBody PmsProductCategoryParam productCategoryParam,
                         BindingResult result) {
        int count = productCategoryService.update(id, productCategoryParam);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @ApiOperation("删除商品分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:productCategory:delete')")
    public Object delete(@PathVariable Long id) {
        int count = productCategoryService.delete(id);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("添加产品分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:productCategory:create')")
    public Object create(@Validated
                         @RequestBody PmsProductCategoryParam productCategoryParam) {
        int count = productCategoryService.create(productCategoryParam);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }
}
