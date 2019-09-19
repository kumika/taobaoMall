# taobaoMall
mall项目是一套电商系统，包括前台商城系统及后台管理系统，基于SpringBoot+MyBatis实现。

# 电商项目--2





#商品
商品的数据库表格：
[![](https://ae01.alicdn.com/kf/H5f4193839d4746bc805a9c13dd46ef5ar.jpg)](https://ae01.alicdn.com/kf/H5f4193839d4746bc805a9c13dd46ef5ar.jpg)

[![](https://cy-pic.kuaizhan.com/g3/5a/81/ff0f-f2fb-4446-a22c-d518a76255a709)](https://cy-pic.kuaizhan.com/g3/5a/81/ff0f-f2fb-4446-a22c-d518a76255a709)

#商品列表


##查询全部的商品
就是分页查询商品列表

要求：
点击链接，跳转后自动查询商品列表，分页的。
输入参数：商品对象属性参数 ，当前页码数，页数


前端请求：

在index.vue上
```
  import {
    fetchList,
    updateDeleteStatus,
    updateNewStatus,
    updateRecommendStatus,
    updatePublishStatus
  } from '@/api/product'
```

在API的product上
```
export function fetchList(params) {
  return request({
    url:'/product/list',
    method:'get',
    params:params
  })
}
```

前端要传输的数据参数（就是前后端相互沟通好的参数，而且是默认值）：
```
  const defaultListQuery = {
    keyword: null,
    pageNum: 1,
    pageSize: 5,
    publishStatus: null,
    verifyStatus: null,
    productSn: null,
    productCategoryId: null,
    brandId: null
  };
```
参数赋值：
defaultListQuery赋值给listQuery
```
 listQuery: Object.assign({}, defaultListQuery),
 //一大串代码
  fetchList(this.listQuery).then(response => {····
 
```


后端代码：

PmsProductController：

参数：
**productQueryParam,  pageSize,  pageNum**
  
```
@ApiOperation("查询商品")
@RequestMapping(value = "/list", method = RequestMethod.GET)
@ResponseBody
//限制查询，只能查询拥有权限的read部分，这是Spring Security的注解
@PreAuthorize("hasAuthority('pms:product:read')")
 public Object getlist(PmsProductQueryParam productQueryParam,
                    @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                    @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = productService.list(productQueryParam, pageSize, pageNum);
        return new CommonResult().pageSuccess(productList);
    }
```
其中参数productQueryParam是自己根据客户需求创建的产品参数：（就是前后端相互沟通好的参数）
```
import io.swagger.annotations.ApiModelProperty;
public class PmsProductQueryParam {

    @ApiModelProperty("上架状态")
    private Integer publishStatus;

    @ApiModelProperty("审核状态")
    private Integer verifyStatus;

    @ApiModelProperty("商品名称模糊关键字")
    private String keyword;

    @ApiModelProperty("商品货号")
    private String productSn;

    @ApiModelProperty("商品分类编号")
    private Long productCategoryId;

    @ApiModelProperty("商品品牌编号")
    private Long brandId;
    
    //getting 和setting
```


Service：
客户需求：  点击连接，完成查询全部商品，得到全部商品的信息
输入参数： 上架状态publishStatus，审核状态verifyStatus，关键字keyword，商品货号productSn，商品分类编号productCategoryId，商品品牌编号brandId
exmaple： 查询的是商品对象，所以使用PmsProductExample,同时又条件要求： 6个参数条件要求，所以example也要增加条件语句criteria，增加那和参数名一致的方法，
比如上架状态publishStatus就使用andPublishStatusEqualTo方法，审核状态verifyStatus使用andVerifyStatusEqualTo，
关键字keyword使用andNameLike（“%”+keyword+“%”）方法，
商品货号productSn使用andProductSnEqualTo，
商品分类编号productCategoryId使用andBrandIdEqualTo方法，
商品品牌编号brandId使用andProductCategoryIdEqualTo，
记得这些方法都是需要上面的输入参数的，这里没有写参数而已。

完整代码:
```
    /**
     * 分页查询商品
     */
      List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);
```
Impl:
```
import com.github.pagehelper.PageHelper//MyBatis分页插件
    @Override
    public List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);

        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);

        //上架状态
        if (productQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        //审核状态
        if (productQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        //商品名称模糊关键字
        if (!StringUtils.isEmpty(productQueryParam.getKeyword())) {
            criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
        }

        //商品货号
        if (!StringUtils.isEmpty(productQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(productQueryParam.getProductSn());
        }

        //商品分类编号
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }

        //商品品牌编号
        if (productQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
        }


        return productMapper.selectByExample(productExample);
    }
```



##商品的增删查操作

## 1 单一商品查询

需求:
勾选一个商品，点击编辑按钮，跳转到另外一个网页，把该商品信息从数据库读取出来。
[![商品编辑操作.jpg](https://i.loli.net/2019/07/23/5d35e8a41d86030165.jpg)](https://i.loli.net/2019/07/23/5d35e8a41d86030165.jpg)

点击这个编辑按钮，跳转到商品详细页面（不要问怎么跳转，前端的事情），此时前端发出获取该商品信息的请求
就是在创建商品信息页面的同时发出请求
```
    created(){
      if(this.isEdit){
        getProduct(this.$route.query.id).then(response=>{
          this.productParam=response.data;
        });
      }
    }
```


后端代码：
PmsProductController:
接收要查询的商品id，然后进行查询
```
    @ApiOperation("根据商品id获取商品编辑信息")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:product:read')")
    public Object getUpdateInfo(@PathVariable Long id) {
        PmsProductResult productResult = productService.getUpdateInfo(id);
        return new CommonResult().success(productResult);
    }
```


service:
接口：
```
/**
 * 根据商品编号获取更新信息
 */
    PmsProductResult getUpdateInfo(Long id);
}
```
实现类：
```
    public PmsProductResult getUpdateInfo(Long id) {
        return productDao.getUpdateInfo(id);
    }
```

Dao:

接口：
```
    /**
     * 获取商品编辑信息
     */
    PmsProductResult getUpdateInfo(@Param("id") Long id);
```


XML文件:
```
 <resultMap id="updateInfoMap" type="com.taobao.taobaoadmin.dto.Pms.PmsProductResult" extends="com.taobao.taobaoadmin.mapper.PmsProductMapper.ResultMapWithBLOBs">
        <result column="cateParentId" jdbcType="BIGINT" property="cateParentId" />
        <collection property="productLadderList" columnPrefix="ladder_" resultMap="com.taobao.taobaoadmin.mapper.PmsProductLadderMapper.BaseResultMap"/>
        <collection property="productFullReductionList" columnPrefix="full_" resultMap="com.taobao.taobaoadmin.mapper.PmsProductFullReductionMapper.BaseResultMap"/>
        <collection property="memberPriceList" columnPrefix="member_" resultMap="com.taobao.taobaoadmin.mapper.PmsMemberPriceMapper.BaseResultMap"/>
        <collection property="skuStockList" columnPrefix="sku_" resultMap="com.taobao.taobaoadmin.mapper.PmsSkuStockMapper.BaseResultMap"/>
        <collection property="productAttributeValueList" columnPrefix="attribute_" resultMap="com.taobao.taobaoadmin.mapper.PmsProductAttributeValueMapper.BaseResultMap"/>
</resultMap>


    <select id="getUpdateInfo" resultMap="updateInfoMap">
        SELECT  *,
                pc.parent_id as cateParentId,
                l.id as ladder_id, l.product_id as ladder_product_id, l.discount as ladder_discount, l.count as ladder_count, l.price as ladder_price,
                pf.id as full_id, pf.product_id as full_product_id, pf.full_price as full_full_price,pf.reduce_price as  full_reduce_price,
                m.id as member_id,m.product_id as member_product_id,m.member_level_id as member_member_level_id,m.member_price as member_member_price,m.member_level_name as member_member_level_name,
                s.id as sku_id,s.product_id as sku_product_id,s.price as sku_price,s.low_stock as sku_low_stock,s.pic as sku_pic,s.sale,s.sku_code as sku_sku_code,s.sp1 as sku_sp1,s.sp2 as sku_sp2,s.sp3 as sku_sp3,s.stock as sku_stock,
                a.id as attribute_id,a.product_id as attribute_product_id,a.product_attribute_id as attribute_product_attribute_id,a.value as attribute_value
        FROM pms_product p
        LEFT  JOIN pms_product_category pc on pc.id = p.product_category_id
        LEFT  JOIN pms_product_ladder l on p.id = l.product_id
        left  JOIN pms_product_full_reduction pf on pf.product_id = p.id
        LEFT  JOIN pms_member_price m on m.product_id = p.id
        LEFT  JOIN pms_sku_stock s ON s.product_id = p.id
        LEFT  JOIN pms_product_attribute_value a ON a.product_id = p.id
        WHERE p.id = #{id};
    </select>
```
其中MyBatis的Collecion是没有使用过的。
Collection标签property的各种`list变量`是原本映射来自对象`PmsProductParam`的属性，但是因为对象`PmsProductResult`是继承了`PmsProductResult`，所以也就是现在映射`PmsProductResult`的属性

    PmsProductResult(查询单个产品进行修改时返回的结果)《----继承----PmsProductParam(创建和修改商品时使用的参数)《----继承-----PmsProduct(商品基本参数对象)

查询这么多的属性，使用标签`columnPrefix`进行别名区分。columnPrefix是自动将前缀增加到SQL语句上，方便增删查操作。（自己个人理解： 当出现多个表查询时，方便区分所要的属性，不会重复，resultMap上的`columnPrefix`要和查询语句上的属性别名前缀一致）

**columnPrefix标签参考：**
https://blog.csdn.net/chris_mao/article/details/48863609
https://www.2cto.com/database/201604/501256.html

###6个表联合商品表查询
商品表 pms_product  p 

    左接    商品分类表 pms_product_category pc 
    连接条件    2个字段的id相同  pc.id = p.product_category_id
    左接    商品阶梯价格价格表 pms_product_ladder l 
    连接条件    2个字段的id相同 p.id = l.product_id
    左接    商品满减价格表   pms_product_full_reduction pf
    连接条件    2个字段的id相同 pf.product_id = p.id
    左接    商品会员价格表  pms_member_price   m
    连接条件    2个字段的id相同 m.product_id = p.id
    左接    商品sku库存表   pms_sku_stock   s
    连接条件    2个字段的id相同 s.product_id = p.id
    左接    商品参数及自定义规格属性表 pms_product_attribute_value a 
    连接条件    2个字段的id相同  a.product_id = p.id






## 2 商品增加

增加商品，是有多个功能组合成一个实现，上传图片是其中之一，

### 2.1上传图片：

参考：https://www.jianshu.com/p/6aebdca025fc

流程图：
[![上图.jpg](https://i.loli.net/2019/06/05/5cf7924d4fd7b94959.jpg)](https://i.loli.net/2019/06/05/5cf7924d4fd7b94959.jpg)

Web前端请求应用服务器，获取上传所需参数（如OSS的accessKeyId、policy、callback等参数）
应用服务器返回相关参数
Web前端直接向OSS服务发起上传文件请求
等上传完成后OSS服务会回调应用服务器的回调接口
应用服务器返回响应给OSS服务
OSS服务将应用服务器回调接口的内容返回给Web前端


####整合OSS实现文件上传
-----------

#####**在pom.xml中添加相关依赖**
```
		<!-- 阿里云OSS -->
		<dependency>
			<groupId>com.aliyun.oss</groupId>
			<artifactId>aliyun-sdk-oss</artifactId>
			<version>2.5.0</version>
		</dependency>
```

#####**修改SpringBoot配置文件**
注意：endpoint、accessKeyId、accessKeySecret、bucketName、callback、prefix都要改为你自己帐号OSS相关的，callback需要是公网可以访问的地址。

写在application.properties上
```
#===OSS start===
aliyun.oss.endpoint=oss-cn-shenzhen.aliyuncs.com
aliyun.oss.accessKeyId=LTAI65MAAZHXwAYS
aliyun.oss.accessKeySecret=FQEMBK6kko6OqLlCpYJDmuA2ygzRL0
aliyun.oss.bucketName=ima-oss
aliyun.oss.policy.expire=300
aliyun.oss.maxSize=10
aliyun.oss.callback=http://loc                                                                                                                                                                     alhost:8080/aliyun/oss/callback
aliyun.oss.dir.prefix=mall/images/
#===OSS end===
```

配置说明:

    endpoint: oss-cn-shenzhen.aliyuncs.com # oss对外服务的访问域名
    accessKeyId: test # 访问身份验证中用到用户标识
    accessKeySecret: test # 用户用于加密签名字符串和oss用来验证签名字符串的密钥
    bucketName: macro-oss # oss的存储空间
    policy:
      expire: 300 # 签名有效期(S)
    maxSize: 10 # 上传文件大小(M)
    callback: http://localhost:8080/aliyun/oss/callback # 文件上传成功后的回调地址
    dir:
      prefix: mall/images/ # 上传文件夹路径前缀



#####**添加OSS的相关Java配置**




用于配置OSS的连接客户端OSSClient

```
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OssConfig {
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.accessKeyId}")
    private String ALIYUN_OSS_ACCESSKEYID;
    @Value("${aliyun.oss.accessKeySecret}")
    private String ALIYUN_OSS_ACCESSKEYSECRET;
    @Bean
    public OSSClient ossClient(){
        return new OSSClient(ALIYUN_OSS_ENDPOINT,ALIYUN_OSS_ACCESSKEYID,ALIYUN_OSS_ACCESSKEYSECRET);
    }
}
```

#####**添加OSS上传策略封装对象OssPolicyResult**
前端直接上传文件时所需参数，从后端返回过来。

```
import io.swagger.annotations.ApiModelProperty;
/**
 * 获取OSS上传授权返回结果
 */
public class OssPolicyResult {
    @ApiModelProperty("访问身份验证中用到用户标识")
    private String accessKeyId;
    @ApiModelProperty("用户表单上传的策略,经过base64编码过的字符串")
    private String policy;
    @ApiModelProperty("对policy签名后的字符串")
    private String signature;
    @ApiModelProperty("上传文件夹路径前缀")
    private String dir;
    @ApiModelProperty("oss对外服务的访问域名")
    private String host;
    @ApiModelProperty("上传成功后的回调设置")
    private String callback;
    //省略了getter和setter
}
```

#####**添加OSS上传成功后的回调参数对象OssCallbackParam**

当OSS上传成功后，会根据该配置参数来回调对应接口。

```
import io.swagger.annotations.ApiModelProperty;

/**
 * oss上传成功后的回调参数
 */
public class OssCallbackParam {
    @ApiModelProperty("请求的回调地址")
    private String callbackUrl;
    @ApiModelProperty("回调是传入request中的参数")
    private String callbackBody;
    @ApiModelProperty("回调时传入参数的格式，比如表单提交形式")
    private String callbackBodyType;
    //省略getter和setter
}
```


#####**OSS上传成功后的回调结果对象OssCallbackResult**

回调接口中返回的数据对象，封装了上传文件的信息。

```
/**
 * oss上传文件的回调结果
 */
public class OssCallbackResult {
    @ApiModelProperty("文件名称")
    private String filename;
    @ApiModelProperty("文件大小")
    private String size;
    @ApiModelProperty("文件的mimeType")
    private String mineType;
    @ApiModelProperty("图片文件的宽")
    private String width;
    @ApiModelProperty("图片文件的高")
    private String height;
    //省略getter和setter
}
```


#####**OssController定义接口**

```
import com.taobao.taobaoadmin.dto.CommonResult;
import com.taobao.taobaoadmin.dto.OssCallbackResult;
import com.taobao.taobaoadmin.dto.OssPolicyResult;
import com.taobao.taobaoadmin.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Oss相关操作接口
 */
@Controller
@Api(tags = "OssController",description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy",method = RequestMethod.GET)
    @ResponseBody
    public Object policy() {
        OssPolicyResult result = ossService.policy();
        return new CommonResult().success(result);
    }

    @ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value="callback",method = RequestMethod.POST)
    @ResponseBody
    public Object callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return new CommonResult().success(ossCallbackResult);
    }

}
```

#####**OSS业务接口OssService**

```
import com.taobao.taobaoadmin.dto.OssCallbackResult;
import com.taobao.taobaoadmin.dto.OssPolicyResult;
import javax.servlet.http.HttpServletRequest;

/**
 * oss上传管理Service
 */
public interface OssService {
   //oss上传策略生成
    OssPolicyResult policy();

    //oss上传成功回调
    OssCallbackResult callback(HttpServletRequest request);
}
```

#####实现类OssServiceImpl

```
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.taobao.taobaoadmin.dto.OssCallbackResult;
import com.taobao.taobaoadmin.dto.OssPolicyResult;
import com.taobao.taobaoadmin.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OssServiceImpl implements OssService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);

    @Value("${aliyun.oss.policy.expire}")
    private int ALIYUN_OSS_EXPIRE;
    @Value("${aliyun.oss.maxSize}")
    private int ALIYUN_OSS_MAX_SIZE;
    @Value("${aliyun.oss.callback}")
    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;


    @Autowired
    private OSSClient ossClient;


    /**
     * 签名生成
     *签名直传服务,响应客户端发送给应用服务器的GET消息(在Controller上看get或者POST)
     * @return
     */
    @Override
    public OssPolicyResult policy() {
        OssPolicyResult result = new OssPolicyResult();
        //存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = ALIYUN_OSS_DIR_PREFIX + sdf.format(new Date());

        //签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);

        //文件大小
        long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;

        //提交节点
        String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;

        try {
            PolicyConditions policyCond = new PolicyConditions();
            policyCond.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyCond.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyCond);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signatrue = ossClient.calculatePostSignature(postPolicy);

            //访问身份验证中用到用户标识
            result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            //用户表单上传的策略，经过Base64编码过的字符串
            result.setPolicy(policy);
            //对policy签名后的字符串
            result.setSignature(signatrue);
            //上传文件夹路径前缀
            result.setDir(dir);
            //OSS对外服务的访问域名
            result.setHost(action);
        } catch (Exception e) {
            LOGGER.error("签名生成失败", e);
        }

        return result;
    }

    @Override
    public OssCallbackResult callback(HttpServletRequest request) {
        OssCallbackResult result = new OssCallbackResult();
        String filename = request.getParameter("filename");
        filename = "http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);

        result.setFilename(filename);
        result.setSize(request.getParameter("size"));
        result.setMineType(request.getParameter("mimeType"));
        result.setWidth(request.getParameter("width"));
        result.setHeight(request.getParameter("height"));

        return result;
    }
}
```

是参考使用阿里的API实现上传的，就是改变了一些变量的名字。
API参考：
https://help.aliyun.com/document_detail/91868.html?spm=a2c4g.11186623.2.15.2b9a6e28ifkhfH#concept-ahk-rfz-2fb



###2.2 增加产品请求

添加商品这个过程中会发出请求，
增加请求又分2步骤：

    第一步是向数据库查询数据请求
    第2步是向数据库插入数据请求

因为有些商品属性是需要先查询，然后让用户选择属性作为商品属性的，所以会出现先一个查询请求，然后接着一个插入请求。
下面就是商品的查询请求：

    查询品牌对象
    查询商品分类对象 
    查询商品属性类型对象
    查询商品关联

上面都是访问增加商品页面时候需要加载的对象，不是客户自己添加商品的属性，下面才是客户自己添加的属性内容

自己查看商品对象的属性，然后写前端发出的要求是什么

整理整个过程
插入Mybatis的SQL语句


####2.2.1查询请求
前端点击lia：  

点击链接，跳转增加商品链接

点击页面旁边的状态栏，引入模块，发出跳转连接
  

    component: () => import('@/views/pms/product/add')


跳转到Add.vue上，随后有引入模块，跳转到Product Detail.vue上

  

    import ProductDetail from './components/ProductDetail'


跳转到productdetail.vue上，引入模块，跳转到5个模块上，发出4个查询请求，InfoDetail这个是后面选择好ID查询时候才使用的。

      import ProductInfoDetail from './ProductInfoDetail';
      import ProductSaleDetail from './ProductSaleDetail';
      import ProductAttrDetail from './ProductAttrDetail';
      import ProductRelationDetail from './ProductRelationDetail';
      import {createProduct,getProduct,updateProduct} from '@/api/product';



#####2.2.1.1查询品牌对象和查询商品1级2级分类请求

从**ProductInfoDetail**模块上引入品牌模块,查询产品分类模块

      import {fetchListWithChildren} from '@/api/productCate'
      import {fetchList as fetchBrandList} from '@/api/brand'
      import {getProduct} from '@/api/product';

从API文件下的ProductCate模块,brand模块上分别发出**查询品牌对象请求**和**查询商品1级2级分类请求**

######2.2.1.1.1查询品牌对象请求

```
export function fetchList(params) {
  return request({
    url:'/brand/list',
    method:'get',
    params:params
  })
}
```

PmsBrandController：
keyWord，pageNum, pageSize这些参数在前端的表单上已经包装好，我们后端只是需要知道传入参数的名称，值是多少，就可以了。个人猜想是前后端需要沟通好才能写这参数。
```
@Controller
@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService brandService;

    @ApiOperation(value = "根据品牌名称分页获取品牌列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public Object getList(@RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(brandService.listBrand(keyword, pageNum, pageSize));
    }

}
```


Service
pageHelper是MyBatis的分页插件
客户的需求： 根据关键词keyword查询产品
输入参数：查询关键词keyword
criteria采用的方法：andNameLike("%"+ keyword +"%")
```
import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.mapper.PmsBrandMapper;
import com.taobao.taobaoadmin.model.PmsBrandExample;
import com.taobao.taobaoadmin.service.Pms.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class PmsBrandServiceImpl implements PmsBrandService{

    @Autowired
    private PmsBrandMapper brandMapper;

    @Override
    public List listBrand(String keyword, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria = pmsBrandExample.createCriteria();

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }

        return brandMapper.selectByExample(pmsBrandExample);
    }
}
```



######2.2.1.1.2查询商品1级2级分类请求
```
export function fetchListWithChildren() {
  return request({
    url:'/productCategory/list/withChildren',
    method:'get'
  })
```

PmsProductCategoryController：
查询全部的商品1级2级分类
```
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
}
```

Service
客户需求： 查找产品，而且产品带有分类属性，分类属性有1级2级
输入： 没有参数
Dao： 使用自己写的SQL查询

```
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryDao productCategoryDao;

    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        return productCategoryDao.listWithChildren();
    }
}
```


Dao：

创建带2级分类产品的分类对象`PmsProductCategoryWithChildrenItem`，继承`PmsProductCategory`类，它带有一个`PmsProductCategory`类型的属性：`children`
说明结果集：
创建一个结果集`listWithChildrenMap`，返回的类型是一个带2级分类产品的分类对象`PmsProductCategoryWithChildrenItem`，然后继承产品分类对象的结果集，同时`listWithChildrenMap`有Collection标签，Collection设置标签的属性：属性前缀`child_`和JavaBean的属性`children`


查询语句：
pms_product_category 是数据库中的产品分类表，用现实的话就是一张表复印2份，一份称为C1，另一份称为C2，然后使用`左连接`，连接2份表。C1是主表格，C2是副表格。
连接2份表格的连接条件是ON之后的**（副表条件在On之后）**
```
c1.id = c2.parent_id
```
主要筛选条件是where之后的**（主表条件在where之后）**
```
c1.parent_id = 0
```
**查询全部的产品1级和2级分类对象----查询条件：字段`parentId = 0` 的产品，而且它的子类的`parentid`与自己的`id`相同**


完整查询语句：
```
    <resultMap id="listWithChildrenMap"   type="com.taobao.taobaoadmin.dto.Pms.PmsProductCategoryWithChildrenItem"
               extends="com.taobao.taobaoadmin.mapper.PmsProductCategoryMapper.BaseResultMap">
               
<!--
        collection 为关联关系，是实现一对多的关键
　　　　1. property 为javabean中容器对应字段名
　　　　2. ofType 指定集合中元素的对象类型
　　　　3. select 使用另一个查询封装的结果
　　　　4. column 为数据库中的列名，与select配合使用
        columnPrefix是表示在Collection所包括的字段，在SQL书写的时候，这些字段自动添加上“XXX”的前缀，与其他字段名区别开
        这里是把全部字段都加上”child_“前缀。
-->           
<!--
column 对应数据库表的列名
property 属性对应javabean的属性名
-->   
        <collection property="children" resultMap="com.taobao.taobaoadmin.mapper.PmsProductCategoryMapper.BaseResultMap"
                    columnPrefix="child_"/>
    </resultMap>
    <select id="listWithChildren" resultMap="listWithChildrenMap">
        SELECT
            c1.id,
            c1.name,
            c2.id   as child_id,
            c2.name as child_name
        FROM
            pms_product_category c1
        left join  pms_product_category c2 ON  c1.id = c2.parent_id
        WHERE  c1.parent_id = 0
    </select>

```

前端发出请求：查询全部的产品对象而且是带有子类的产品分类对象（就是一个分类产品名称里带有另外一个分类产品名称）

**查询全部的产品1级和2级分类对象----查询条件：字段`parentId = 0` 的产品，而且它的子类的`parentid`与自己的`id`相同**



#####2.2.1.2查询获取全部商品专题和获取所有商品优选

从**ProductRelationDetail**模块上引入商品专题模块，引入商品优选模块

      import {fetchListAll as fetchSubjectList} from '@/api/subject'
      import {fetchList as fetchPrefrenceAreaList} from '@/api/prefrenceArea'


从API文件下的subject模块,prefrenceArea模块上分别发出**查询全部商品专题请求**和**查询所有商品优选请求**

######2.2.1.2.1获取所有商品优选
请求进来是没有一个参数的。
```
export function fetchList() {
  return request({
    url:'/prefrenceArea/listAll',
    method:'get',
  })
  }
```

CmsPreferenceAreaController：
请求进来是没有一个参数的。
```
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
```

Service

客户需求： 查询所有商品
输入： 没有参数的请求
example： 使用Mybatis的时候，自动创建的CmsPrefrenceAreaExample现在直接使用，不加入Criteria语句就是查询全部了。查询什么产品类就使用Mybatis自动创建的那个产品类

```
import com.taobao.taobaoadmin.mapper.CmsPrefrenceAreaMapper;
import com.taobao.taobaoadmin.model.CmsPrefrenceArea;
import com.taobao.taobaoadmin.model.CmsPrefrenceAreaExample;
import com.taobao.taobaoadmin.service.Cms.CmsPreferenceAreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CmsPreferenceAreServiceImpl implements CmsPreferenceAreService {

    @Autowired
    private CmsPrefrenceAreaMapper preferenceAreaMapper;

    @Override
    public List<CmsPrefrenceArea> listAll() {
        return preferenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
    }
}
```



######2.2.1.2.2 查询获取全部商品专题
```
export function fetchListAll() {
  return request({
    url:'/subject/listAll',
    method:'get',
  })
}
```


CmsSubjectController：
```
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
}
```

Service
客户需求： 查询所有商品专题
输入： 没有参数的请求
example： 查询产品是商品专题类，所以使用CmsSubjectExample

```
@Service
public class CmsSubjectServiceImpl implements CmsSubjectService {

    @Autowired
    private CmsSubjectMapper subjectMapper;

    @Override
    public List<CmsSubject> listAll() {
        return subjectMapper.selectByExample(new CmsSubjectExample());
    }
}
```




#####2.2.1.3查询商品属性分类和获取所有商品属性

从**ProductAttrDetail**模块上引入商品属性分类模块，引入商品属性模块,后面的就是下载了，那就是接上面2.1的上传图片内容了。

      import {fetchList as fetchProductAttrCateList} from '@/api/productAttrCate'
      import {fetchList as fetchProductAttrList} from '@/api/productAttr'
      import SingleUpload from '@/components/Upload/singleUpload'
      import MultiUpload from '@/components/Upload/multiUpload'
      import Tinymce from '@/components/Tinymce'


从API文件下的productAttriCate模块,productAttr模块上分别发出**查询分页获取所有商品属性分类请求**和**根据商品的分类查询商品的属性列表或参数列表请求**

######2.2.1.3.1查询分页获取所有商品属性分类请求
参数是分页的参数，前端沟通的。
```
export function fetchList(params) {
  return request({
    url:'/productAttribute/category/list',
    method:'get',
    params:params
  })
}
```
PmsProductAttributeCategoryController 
请求的参数是分页pageSize,pageNum，这是与前端沟通好的。
返回的是全部产品属性的分类对象
```
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
}
```

Service
客户需求： 查询所有商品属性的分类对象
输入： 有分页参数的请求 pageSize，pageNum
example： 查询的是商品属性的分类对象，所以使用PmsProductAttributeCategoryExample


```
@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;


    @Override
    public List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);

        return productAttributeCategoryMapper.selectByExample(new PmsProductAttributeCategoryExample());
    }
}
```



######2.2.1.3.2根据商品的分类查询商品的属性列表请求或参数列表
客户要的效果就是 在创建商品过程中根据商品分类类型 查询分类的属性列表或者参数列表**（注意：这里的属性和参数的区别只是在数据库中商品属性表的字段type值的不同，并不是不同表格的字段之类）**

效果：
[![创建商品使用到了根据分类对象查询商品属性.jpg](https://i.loli.net/2019/07/23/5d35e0b3cf60765218.jpg)](https://i.loli.net/2019/07/23/5d35e0b3cf60765218.jpg)
选择商品分类，获取商品属性，然后返回到页面给客户进行编辑商品属性
比如：
服装--T恤商品的属性编辑
[![商品属性1.jpg](https://i.loli.net/2019/08/01/5d41faa9b996435974.jpg)](https://i.loli.net/2019/08/01/5d41faa9b996435974.jpg)
[![商品属性2.jpg](https://i.loli.net/2019/08/01/5d41faa9cbd4f35007.jpg)](https://i.loli.net/2019/08/01/5d41faa9cbd4f35007.jpg)

手机商品的属性编辑：
[![商品属性3.jpg](https://i.loli.net/2019/08/01/5d41faed2d8c282240.jpg)](https://i.loli.net/2019/08/01/5d41faed2d8c282240.jpg)
[![商品属性4.jpg](https://i.loli.net/2019/08/01/5d41faed208f043172.jpg)](https://i.loli.net/2019/08/01/5d41faed208f043172.jpg)

前端请求：
客户的想法：点击选择商品的分类，获取商品的属性，然后进行修改商品的属性

```      
<el-form-item label="属性类型：">
        <el-select v-model="value.productAttributeCategoryId"
                   placeholder="请选择属性类型"
                   @change="handleProductAttrChange">
    //一大串代码····               
      import {fetchList as fetchProductAttrList} from '@/api/productAttr'
    //一大串代码····
      handleProductAttrChange(value) {
        this.getProductAttrList(0, value);
        this.getProductAttrList(1, value);
      },
      //一大串代码····
      getProductAttrList(type, cid) {
        let param = {pageNum: 1, pageSize: 100, type: type};
        fetchProductAttrList(cid, param).then(response => {
```

商品属性的API代码：
```
export function fetchList(cid,params) {
  return request({
    url:'/productAttribute/list/'+cid,
    method:'get',
    params:params
  })
}
```

具体的请求参数名
```
 let param = {pageNum: 1, pageSize: 100, type: type};
        fetchProductAttrList(cid, param).then(response => {
```


后端：

PmsProductAttributeController 
请求参数有4个：商品分类cid，type，pageSize，pageNum，也是与前端沟通好的。
```
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
}

```

Service
客户需求： 根据商品分类id和产品类型type查询所有的商品属性对象
输入： 有分页参数的请求 pageSize，pageNum, 商品类型type, 商品分类ID--cid
example： 查询的是商品属性对象，所以使用PmsProductAttributeExample,同时又条件要求： cid，type，所以example也要增加条件语句criteria，增加方法idEqualTo(),和方法andTypeEqualTo()

```
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {

    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;
    
    @Override
    public List<PmsProductAttribute> getList(Long cid,Integer type , Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
        return productAttributeMapper.selectByExample(example);
    }
}
```




#####2.2.1.4查询会员等级

从ProductSaleDetail模块引入会员模块

    import {fetchList as fetchMemberLevelList} from '@/api/memberLevel'

从API文件下的memberLevel模块上发出**查询会员等级请求**

```
export function fetchList(params) {
  return request({
    url:'/memberLevel/list',
    method:'get',
    params:params
  })
}
```
请求参数params的具体参数名
```
    fetchMemberLevelList({defaultStatus: 0}).then(response => {
```

UmsMemberLevelController：
请求参数：defaultStatus,也是与前端沟通好的
```
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
```

Service 
客户需求： 查询会员等级
输入： defaultStatus 会员等级状态
example： 查询的是会员等级对象，所以使用UmsMemberLevelExample,同时有条件要求：会员等级状态defaultStatus，所以example也要增加条件语句criteria，增加方法andDefaultStatusEqualTo（）
```
@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService {

    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;

    @Override
    public List<UmsMemberLevel> list(Integer defaultStatus) {

        UmsMemberLevelExample example = new UmsMemberLevelExample();
        example.createCriteria().andDefaultStatusEqualTo(defaultStatus);
        return memberLevelMapper.selectByExample(example);
    }
}
```



####2.2.2产品的创建请求
因为是在Product Detail.vue上引入

     import {createProduct,getProduct,updateProduct} from '@/api/product';

所以其实是使用 **创建，查询，更新** 3个操作的**（创建的第一反应就是插入数据，为什么老是没反应过来啊）**


这里就只说引入product模块的createProduct函数


从API文件下的product模块上发出**创建商品请求**

```
export function createProduct(data) {
  return request({
    url:'/product/create',
    method:'post',
    data:data
  })
}
```
具体的请求参数data
```
createProduct(this.productParam).then(response=>{
```
productParam在前端的定义：
```
  productParam: Object.assign({}, defaultProductParam),
```
defaultProductParam在前端的定义：
```
  const defaultProductParam = {
    albumPics: '',
    brandId: null,
    brandName: '',
    deleteStatus: 0,
    description: '',
    detailDesc: '',
    detailHtml: '',
    detailMobileHtml: '',
    detailTitle: '',
    feightTemplateId: 0,
    flashPromotionCount: 0,
    flashPromotionId: 0,
    flashPromotionPrice: 0,
    flashPromotionSort: 0,
    giftPoint: 0,
    giftGrowth: 0,
    keywords: '',
    lowStock: 0,
    name: '',
    newStatus: 0,
    note: '',
    originalPrice: 0,
    pic: '',
    //会员价格{memberLevelId: 0,memberPrice: 0,memberLevelName: null}
    memberPriceList: [],
    //商品满减
    productFullReductionList: [{fullPrice: 0, reducePrice: 0}],
    //商品阶梯价格
    productLadderList: [{count: 0,discount: 0,price: 0}],
    previewStatus: 0,
    price: 0,
    productAttributeCategoryId: null,
    //商品属性相关{productAttributeId: 0, value: ''}
    productAttributeValueList: [],
    //商品sku库存信息{lowStock: 0, pic: '', price: 0, sale: 0, skuCode: '', sp1: '', sp2: '', sp3: '', stock: 0}
    skuStockList: [],
    //商品相关专题{subjectId: 0}
    subjectProductRelationList: [],
    //商品相关优选{prefrenceAreaId: 0}
    prefrenceAreaProductRelationList: [],
    productCategoryId: null,
    productCategoryName: '',
    productSn: '',
    promotionEndTime: '',
    promotionPerLimit: 0,
    promotionPrice: null,
    promotionStartTime: '',
    promotionType: 0,
    publishStatus: 0,
    recommandStatus: 0,
    sale: 0,
    serviceIds: '',
    sort: 0,
    stock: 0,
    subTitle: '',
    unit: '',
    usePointLimit: 0,
    verifyStatus: 0,
    weight: 0
  };
```

#####2.2.2.1 后端代码


PmsProductController：
请求参数productParam在创建产品的时候它的属性其实不一定全部有值，所以创建一个随时可以修改使用的对象`PmsProductParam`，该对象继承产品对象`PmsProduct`。
作用就是传入的产品的属性存在与数据库不对应的属性的时候，这里可以创建对象创建属性接收传入的产品属性（该属性类型一定是数据库存在的属性）。简单就是可以接收意外的用户要求或者是产品属性。
```
@Controller
@Api(tags = "PmsProductController", description = "商品管理")
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    private PmsProductService productService;

    @ApiOperation("创建商品")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody //把后台pojo转换json对象，返回到页面。
    @PreAuthorize("hasAuthority('pms:product:create')")//限制查询，只能查询拥有权限的create部分，这是Spring Security的注解
    //RequestBody ,接受前台json数据，把json数据自动封装javaBean。
    public Object create(@RequestBody PmsProductParam productParam, BindingResult bindingResult) {
        int count = productService.create(productParam);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }
}
```


Service
这里是每一个产品自定义类属性都需要自己编写SQL
同时都是获取产品类属性对象和产品id随后插入数据库，所以在Service上有重复代码，所以利用反射写可以复用的代码（就是方法，获取参数就可以调用方法完成重复）
复用代码的参数：会员价格，阶梯价格，满减价格，添加sku库存信息，添加商品参数,添加自定义商品规格，关联专题，关联优选


以前是创建XX对象，然后把传入的参数一个个亲自setting进入XX对象中，然后创建一个集合List返回到Controller上

理解复用代码的意思
这段复用好像是利用反射产生对象，然后进行对象赋值的作用，但是目前不知道返回的对象是什么，


这个invoke居然找源码找到了invoke0方法去了，这是一个native方法,它在HotSpot JVM里调用JVM_InvokeMethod函数

输入
过程
输出

这里是我自己没有理解反射的基本概念，导致这段代码没有理解，就是Spring的DI，就是把创建对象这个操作交给程序（spring）。
首先我们根据反射得到Method类型的方法对象login（就是我们要调用的方法，现在根据反射成了一个对象）
我们给出输入参数：使用方法的对象XX，方法需要的参数name（或者是参数列表）
login使用调用invoke(XX,name)，这个过程完成了XX对象对login方法的使用，区别在于，不是我们人写代码完成`XX.login（name）；`这语句，而是由程序完成（AI的最简陋方法？！）写这句代码。



完整的Service代码：
```
@Service
public class PmsProductServiceImpL  implements PmsProductService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpL.class);

    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private PmsMemberPriceDao memberPriceDao;

    @Autowired
    private PmsProductLadderDao productLadderDao;

    @Autowired
    private PmsProductFullReductionDao productFullReductionDao;

    @Autowired
    private PmsSkuStockDao skuStockDao;

    @Autowired
    private PmsProductAttributeValueDao productAttributeValueDao;

    @Autowired
    private CmsSubjectProductRelationDao subjectProductRelationDao;

    @Autowired
    private CmsPrefrenceAreaProductRelationDao prefrenceAreaProductRelationDao;

    @Autowired
    private PmsProductDao productDao;

    /**
     * 建立和插入关系表操作
     * @param dao  可以操作的dao
     * @param dataList  要插入的数据
     * @param productId  建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            if (CollectionUtils.isEmpty(dataList)) return;

            //这里是有规划的，属性列表dataList的主要内容是客户填写的，我们这里只是需要写属性是归类于哪个产品id就可以了
            //这个循环是为了给产品的属性列表中的属性赋予产品ID，表面这个属性是哪个产品哪个ID的。
            for (Object item : dataList) {
                //Method method = ownerClass.getMethod(methodName, argsClass)：
                // 通过methodName和参数的argsClass（方法中的参数类型集合）数组得到要执行的Method。
                // 返回一个方法对象
                Method setId = item.getClass().getMethod("setId", Long.class);
                //method.invoke(owner, args)：执行该Method.invoke方法的参数是执行这个方法的对象owner，和参数数组args，
                //可以这么理解：owner对象中带有参数args的method方法。返回值是Object，也既是该方法的返回值。
                //这里就是根据”setId“获取到XX对象的所有setId方法，当args=null，说明调用的是所有setId方法中的无参数值方法，
                setId.invoke(item, (Long)null);
                //获取XX对象的所有setProductId方法
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                //调用XX对象中的setProductId方法并且方法中参数值要带有productId，返回值是Object
                setProductId.invoke(item, productId);
            }
            //得到插入数据列表方法insertList
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            //dao对象mapper进行插入操作----就是以前写的这句mapper.insertList(XXXList)
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("创建产品出错：{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    
        /**
     * 处理sku的编码，在sku编码后面加入当前日期和商品id
     * @param skuStockList
     * @param productId
     */
    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if (CollectionUtils.isEmpty(skuStockList)) return;

        for (int i = 0; i < skuStockList.size();i++) {
            //获取sku的编码
            PmsSkuStock skuStock = skuStockList.get(i);
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                //对于处理时间短的服务或者启动频率高的要用单线程，相反用多线程
                //单线程操作字符串缓冲流同时有大量数据的时候，使用StringBuilder
                StringBuilder sd = new StringBuilder();
                //日期，获取当前日期
                sd.append(sdf.format(new Date()));
                //四位商品id，%04d是输出商品id不足4位数字则向左用0补齐4位数，d是10进制整型
                sd.append(String.format("%04d", productId));
                //3位索引id，%03d是输出索引id不足3位数字则向左用0补齐3位数，d是10进制整型
                sd.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sd.toString());
            }
        }
    }
        
    /**
     * 创建产品
     * @param productParam
     * @return
     */    
        @Override
    public int create(PmsProductParam productParam) {
        int count;

        //创建商品
        PmsProduct product = productParam;
        product.setId(null);
        productMapper.insertSelective(product);

        //根据促销类型设置价格：、阶梯价格、满减价格
        Long productId = product.getId();

        //会员价格
        relateAndInsertList(memberPriceDao, productParam.getMemberPriceList(), productId);

        //阶梯价格
        relateAndInsertList(productLadderDao, productParam.getProductLadderList(), productId);

        //满减价格
        relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), productId);

        //处理sku的编码，在sku编码后面加入当前日期和商品id
        handleSkuStockCode(productParam.getSkuStockList(), productId);

        //添加sku库存信息
        relateAndInsertList(skuStockDao, productParam.getSkuStockList(), productId);

        //添加商品参数,添加自定义商品规格
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), productId);

        //关联专题
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), productId);

        //关联优选
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), productId);

        count = 1;
        return count;
    }
}
```


Dao
直接写这几个Dao.xml的SQL语句

    注意使用foreach标签的时候，标签里面的属性是POJO的属性，SQL语句的是数据库表格上的字段，老是会头晕把这2个搞混了，或者foreacha标签里的POJO属性写成字段了。别头晕了。


会员价格Dao
PmsMemberPriceDao.xml
```
//插入会员价格
<mapper namespace="com.taobao.taobaoadmin.dao.pms.PmsMemberPriceDao">
    <!--批量新增回写主键支持-->
    <insert id="insertList">
        insert into pms_member_price (product_id,member_level_id,member_price,member_level_name) VALUES
        <foreach collection="list" item="item" separator="," index="index">
            (#{item.productId,jdbcType=BIGINT},
            #{item.memberLevelId,jdbcType=BIGINT},
            #{item.memberPrice,jdbcType=DECIMAL},
            #{item.memberLevelName,jdbcType=VARCHAR})
        </foreach>
    </insert>
</mapper>
```
阶梯价格Dao
PmsProductLadderDao.xml
```
<mapper namespace="com.taobao.taobaoadmin.dao.pms.PmsProductLadderDao">
    <insert id="insertList">
        insert into pms_product_ladder (product_id, count, discount, price) VALUES
        <foreach collection="list" item="item" separator="," index="index">
            (#{item.productId,jdbcType=BIGINT},
            #{item.count,jdbcType=INTEGER},
            #{item.discount,jdbcType=DECIMAL},
            #{item.price,jdbcType=DECIMAL})
        </foreach>
    </insert>
</mapper>
```

满减价格Dao
PmsProductFullReductionDao.xml
```
<mapper namespace="com.taobao.taobaoadmin.dao.pms.PmsProductFullReductionDao">
    <insert id="insertList">
        insert into pms_product_full_reduction (product_id,full_price,reduce_price) VALUES
        <foreach collection="list" item="item" separator="," index="index">
            (#{item.productId,jdbcType=BIGINT},
            #{item.fullPrice,jdbcType=DECIMAL},
            #{item.reducePrice,jdbcType=DECIMAL})
        </foreach>
    </insert>
</mapper>
```


增加sku库存信息
```
<mapper namespace="com.taobao.taobaoadmin.dao.pms.PmsSkuStockDao">

    <insert id="insertList">
        insert into pms_sku_stock (product_id,sku_code,price,stock,low_stock,sp1,sp2,sp3,pic,sale) VALUES
        <foreach collection="list" item="item" separator="," index="index">
            (#{item.productId,jdbcType=BIGINT},
            #{item.skuCode,jdbcType=VARCHAR},
            #{item.price,jdbcType=DECIMAL},
            #{item.stock,jdbcType=INTEGER},
            #{item.lowStock,jdbcType=INTEGER},
            #{item.sp1,jdbcType=VARCHAR},
            #{item.sp2,jdbcType=VARCHAR},
            #{item.sp3,jdbcType=VARCHAR},
            #{item.pic,jdbcType=VARCHAR},
            #{item.sale,jdbcType=INTEGER})
        </foreach>
    </insert>
</mapper>
```

添加商品参数,添加自定义商品规格
PmsProductAttributeValueDao.xml
```
<mapper namespace="com.taobao.taobaoadmin.dao.pms.PmsProductAttributeValueDao">
    <insert id="insertList">
          insert into pms_product_attribute_value (product_id,product_attribute_id,value) VALUES
          <foreach collection="list" item="item" separator="," index="index">
              (#{item.productId,jdbcType=BIGINT},
              #{item.productAttributeId,jdbcType=BIGINT},
              #{item.value,jdbcType=VARCHAR})
          </foreach>
    </insert>
</mapper>
```

关联专题

```
<mapper namespace="com.taobao.taobaoadmin.dao.CMS.CmsSubjectProductRelationDao">
    <insert id="insertList">
          insert into cms_subject_product_relation (subject_id, product_id) values
          <foreach collection="list" item="item" separator="," index="index">
              (#{item.subjectId,jdbcType=BIGINT}, #{item.productId,jdbcType=BIGINT})
          </foreach>
    </insert>
</mapper>
```

关联优选
CmsPrefrenceAreaProductRelationDao.xml
```
<mapper namespace="com.taobao.taobaoadmin.dao.CMS.CmsPrefrenceAreaProductRelationDao">
    <insert id="insertList">
        insert into cms_prefrence_area_product_relation (prefrence_area_id,product_id) values
        <foreach collection="list" item="item" separator="," index="index">
            (#{item.prefrenceAreaId,jdbcType=BIGINT},#{item.productId,jdbcType=BIGINT})
        </foreach>
    </insert>
</mapper>
```





反射定义：
方法的名称和参数列表才能唯一地决定某个方法
方法反射操作：method.invoke(对象，参数列表)
反射是框架底层的一些方法，比如以后看到的Spring的重要特性DI:控制反转就是这么一个原理，至于为什么使用反射，

    1.首先你能通过任意对象获取类类型即是所有的信息，这个作用以后才能体现；
    2.方法的反射的好处就是解耦，比如说a,b,c对象都要调用 print()方法，正常的想法就是要创建每个对象，并且a.print() b.print() c.print() ，但是使用反射的话，就 print()方法的对象.invoke(a,参数列表)想要用哪个对象就用哪个对象










#疑问：

##问题1：
为什么http://localhost:8883不能访问，但是http://localhost:8883/可以访问（会自动添加home）



##问题2 
在MyBatis中的字段别名有什么用
答：
    不会重复字段，导致数据覆盖。


##问题3

```
#===OSS start===
aliyun.oss.endpoint=oss-cn-shenzhen.aliyuncs.com
aliyun.oss.accessKeyId=test
aliyun.oss.accessKeySecret=test
aliyun.oss.bucketName=macro-oss
aliyun.oss.policy.expire=300
aliyun.oss.maxSize=10
aliyun.oss.callback=http://loc                                                                                                                                                                     alhost:8080/aliyun/oss/callback
aliyun.oss.dir.prefix=mall/images/
#===OSS end===
```


出现子用户没有权限的问题。
思路： 给子用户增加权限，在OSS上增加权限。


##问题4：

阿里的OSS跨域设置没有设置好，导致上传图片失败。所以这个跨域设置是什么？



答：
跨域资源共享（Cross-Origin Resource Sharing），简称 CORS，是 HTML5 提供的标准跨域解决方案，OSS 支持 CORS 标准来实现跨域访问。

跨域访问，或者说 JavaScript 的跨域访问问题，是浏览器出于安全考虑而设置的一个限制，即同源策略。举例说明，当 A，B 两个网站属于不同的域时，如果来自于 A 网站的页面中的 JavaScript 代码希望访问 B 网站的时候，浏览器会拒绝该访问。

然而，在实际应用中，经常会有跨域访问的需求。比如用户的网站 www.a.com，后端使用了 OSS，在网页中提供了使用 JavaScript 实现的上传功能，但是在该页面中，只能向 www.a.com 发送请求，向其他网站发送的请求都会被浏览器拒绝。这样会导致用户上传的数据必须从www.a.com 中转。如果设置了跨域访问的话，用户就可以直接上传到 OSS 而无需从 www.a.com 中转。


**使用情景：**

    使用CORS的主要应用就是在浏览器端使用Ajax直接访问OSS的数据，而无需走用户本身的应用服务器中转。无论上传或者下载。对于同时使用OSS和使用Ajax技术的网站来说，都建议使用CORS来实现与OSS的直接通信。



**参考：**
阿里文档：
https://help.aliyun.com/document_detail/31928.html?spm=5176.11065259.1996646101.searchclickresult.4d1a5607Pf3e9i



##问题 5
描述：
上传操作，使用阿里API，然后API中new出OSSClient对象，需要3个参数，但是因为这里用了springMVC，所以spirngMVC注入过程是怎么样的？

答：



##问题6

描述：
因为不清楚SpringMVC与前端进行数据参数交换的过程，与Strust2的堆栈交换是不一致的吗？那SpringMVC是怎么进行参数配对的？


答：
前端和后端数据的交换----转换成JSON类型进行交换----SPringMVC的XML写有JSON的jra包，所以spring就自动转换成JSon类型。
Spring 注解 
@RequestBody   接收前台的json数据，然后自动把JSON数据封装成JAVABEAN
@ResponseBody  把后台实体类POJO对象转换成JSON，返回到前台

使用注解2个都是自动把数据封装好，具体的要看Spring源码
但是POJO对象的属性名字要和前台表单提交的属性名字一致


strust2是不同于SpringMVC的。区别在于strust2会使用ModelDriven接口，使用值栈来，通过类属性Getting，Setting来实现接收OR设置请求对象参数，SpringMVC使用



SpringMVC 是于方法配对，注解适配器对RequestMapping标记的方法进行适配，将从浏览器种请求的数据（key/value或者表单信息）在方法种的形参会进行参数绑定。方法级别的拦截。

    {终极个人理解{SpringMVC的request与Controller怎么适配的----解析URL，根据URL通过XXX适配器，XXX映射器找到，然后匹配到controller}}

Strust2的堆栈配对完成数据交换，请求参数压入堆栈，同时请求参数实现实体类，返回堆栈进行属性配对，配对成功后，访问数据库。类级别的拦截。



SpringMVC的controller向jsp传递数据的五种方式：
https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=1567032756,2290023038&fm=85&s=1FA4D5043A621E8858A01098030050B0https://blog.csdn.net/qq_40646143/article/details/79536909

Request适配Controller参考：
https://www.cnblogs.com/fangjian0423/p/springMVC-request-mapping.html（SpringMVC的参数配对源码解析）
https://www.cnblogs.com/xjz1842/p/6385054.html
https://www.cnblogs.com/xjz1842/p/6392976.html
https://www.cnblogs.com/WuXuanKun/p/6203975.html
https://www.cnblogs.com/fsmly/p/10413163.html（controller的参数绑定）


#参考：

https://www.jianshu.com/p/6aebdca025fc（OSS上传参考）
https://github.com/macrozheng/mall-admin-web


https://www.jianshu.com/p/3d91982ce458
https://github.com/macrozheng/mall/blob/master/document/reference/mysql.md
https://www.cnblogs.com/orac/p/6726323.html（There is no getter for property named 'product_id'）



反射参考：https://blog.csdn.net/lixq05/article/details/78029653

