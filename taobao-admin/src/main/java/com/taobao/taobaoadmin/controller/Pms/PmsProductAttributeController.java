package com.taobao.taobaoadmin.controller.Pms;

import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.dto.Pms.ProductAttrInfo;
import com.taobao.taobaoadmin.model.PmsProductAttribute;
import com.taobao.taobaoadmin.service.Pms.PmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性管理Controller
 */
@Controller
@Api(tags = "PmsProductAttributeController", description = "商品属性管理")
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    private PmsProductAttributeService productAttributeService;

    /*
    *  @ApiImplicitParams是swagger2常用注解
    *
@ApiImplicitParams：用在请求的方法上，表示一组参数说明
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 意思是：请求参数的获取：@RequestHeader
            · query --> 意思是：请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · body（不常用）
            · form（不常用）
        dataType：参数类型，默认String，其它值dataType="Integer"
        defaultValue：参数的默认值
    * */

    @ApiOperation("根据分类查询属性列表或参数列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "type",value="0表示属性，1表示参数",
                                          required = true,
                                          paramType = "query",
                                          dataType = "integer")})
    @RequestMapping(value = "/list/{cid}",method = RequestMethod.GET)
    @ResponseBody
    public Object getList(@PathVariable Long cid,
                          @RequestParam(value="type") Integer type,
                          @RequestParam(value="pageSize",defaultValue = "5") Integer pageSize,
                          @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum) {
        List<PmsProductAttribute> productAttributeList = productAttributeService.getList(cid,type, pageSize, pageNum);

        return new CommonResult().pageSuccess(productAttributeList);
    }


    @ApiOperation("根据商品分类的id获取商品属性及属性分类")
    @RequestMapping(value = "/attrInfo/{productCategoryId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getAttrInfo(@PathVariable Long productCategoryId) {

        List<ProductAttrInfo> productAttrInfoList = productAttributeService.getProductAttrInfo(productCategoryId);

        return new CommonResult().success(productAttrInfoList);
    }



}
