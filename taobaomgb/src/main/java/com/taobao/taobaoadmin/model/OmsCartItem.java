package com.taobao.taobaoadmin.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OmsCartItem implements Serializable {
    private Long id;

    private Long productId;

    private Long productSkuId;

    private Long memberId;

    /**
     * 购买数量
     *
     * @mbggenerated
     */
    private Integer quantity;

    /**
     * 添加到购物车的价格
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * 销售属性1
     *
     * @mbggenerated
     */
    private String sp1;

    /**
     * 销售属性2
     *
     * @mbggenerated
     */
    private String sp2;

    /**
     * 销售属性3
     *
     * @mbggenerated
     */
    private String sp3;

    /**
     * 商品主图
     *
     * @mbggenerated
     */
    private String productPic;

    /**
     * 商品名称
     *
     * @mbggenerated
     */
    private String productName;

    /**
     * 商品副标题（卖点）
     *
     * @mbggenerated
     */
    private String productSubTitle;

    /**
     * 商品sku条码
     *
     * @mbggenerated
     */
    private String productSkuCode;

    /**
     * 会员昵称
     *
     * @mbggenerated
     */
    private String memberNickname;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date modifyDate;

    /**
     * 是否删除
     *
     * @mbggenerated
     */
    private Integer deleteStatus;

    /**
     * 商品分类
     *
     * @mbggenerated
     */
    private Long productCategoryId;

    private String productBrand;

    private String productSn;

    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     *
     * @mbggenerated
     */
    private String productAttr;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSp1() {
        return sp1;
    }

    public void setSp1(String sp1) {
        this.sp1 = sp1;
    }

    public String getSp2() {
        return sp2;
    }

    public void setSp2(String sp2) {
        this.sp2 = sp2;
    }

    public String getSp3() {
        return sp3;
    }

    public void setSp3(String sp3) {
        this.sp3 = sp3;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", productSkuId=").append(productSkuId);
        sb.append(", memberId=").append(memberId);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", sp1=").append(sp1);
        sb.append(", sp2=").append(sp2);
        sb.append(", sp3=").append(sp3);
        sb.append(", productPic=").append(productPic);
        sb.append(", productName=").append(productName);
        sb.append(", productSubTitle=").append(productSubTitle);
        sb.append(", productSkuCode=").append(productSkuCode);
        sb.append(", memberNickname=").append(memberNickname);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", productCategoryId=").append(productCategoryId);
        sb.append(", productBrand=").append(productBrand);
        sb.append(", productSn=").append(productSn);
        sb.append(", productAttr=").append(productAttr);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}