package com.taobao.taobaoadmin.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OmsOrder implements Serializable {
    /**
     * 订单id
     *
     * @mbggenerated
     */
    private Long id;

    private Long memberId;

    private Long couponId;

    /**
     * 订单编号
     *
     * @mbggenerated
     */
    private String orderSn;

    /**
     * 提交时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 用户帐号
     *
     * @mbggenerated
     */
    private String memberUsername;

    /**
     * 订单总金额
     *
     * @mbggenerated
     */
    private BigDecimal totalAmount;

    /**
     * 应付金额（实际支付金额）
     *
     * @mbggenerated
     */
    private BigDecimal payAmount;

    /**
     * 运费金额
     *
     * @mbggenerated
     */
    private BigDecimal freightAmount;

    /**
     * 促销优化金额（促销价、满减、阶梯价）
     *
     * @mbggenerated
     */
    private BigDecimal promotionAmount;

    /**
     * 积分抵扣金额
     *
     * @mbggenerated
     */
    private BigDecimal integrationAmount;

    /**
     * 优惠券抵扣金额
     *
     * @mbggenerated
     */
    private BigDecimal couponAmount;

    /**
     * 管理员后台调整订单使用的折扣金额
     *
     * @mbggenerated
     */
    private BigDecimal discountAmount;

    /**
     * 支付方式：0->未支付；1->支付宝；2->微信
     *
     * @mbggenerated
     */
    private Integer payType;

    /**
     * 订单来源：0->PC订单；1->app订单
     *
     * @mbggenerated
     */
    private Integer sourceType;

    /**
     * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 订单类型：0->正常订单；1->秒杀订单
     *
     * @mbggenerated
     */
    private Integer orderType;

    /**
     * 物流公司(配送方式)
     *
     * @mbggenerated
     */
    private String deliveryCompany;

    /**
     * 物流单号
     *
     * @mbggenerated
     */
    private String deliverySn;

    /**
     * 自动确认时间（天）
     *
     * @mbggenerated
     */
    private Integer autoConfirmDay;

    /**
     * 可以获得的积分
     *
     * @mbggenerated
     */
    private Integer integration;

    /**
     * 可以活动的成长值
     *
     * @mbggenerated
     */
    private Integer growth;

    /**
     * 活动信息
     *
     * @mbggenerated
     */
    private String promotionInfo;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     *
     * @mbggenerated
     */
    private Integer billType;

    /**
     * 发票抬头
     *
     * @mbggenerated
     */
    private String billHeader;

    /**
     * 发票内容
     *
     * @mbggenerated
     */
    private String billContent;

    /**
     * 收票人电话
     *
     * @mbggenerated
     */
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     *
     * @mbggenerated
     */
    private String billReceiverEmail;

    /**
     * 收货人姓名
     *
     * @mbggenerated
     */
    private String receiverName;

    /**
     * 收货人电话
     *
     * @mbggenerated
     */
    private String receiverPhone;

    /**
     * 收货人邮编
     *
     * @mbggenerated
     */
    private String receiverPostCode;

    /**
     * 省份/直辖市
     *
     * @mbggenerated
     */
    private String receiverProvince;

    /**
     * 城市
     *
     * @mbggenerated
     */
    private String receiverCity;

    /**
     * 区
     *
     * @mbggenerated
     */
    private String receiverRegion;

    /**
     * 详细地址
     *
     * @mbggenerated
     */
    private String receiverDetailAddress;

    /**
     * 订单备注
     *
     * @mbggenerated
     */
    private String note;

    /**
     * 确认收货状态：0->未确认；1->已确认
     *
     * @mbggenerated
     */
    private Integer confirmStatus;

    /**
     * 删除状态：0->未删除；1->已删除
     *
     * @mbggenerated
     */
    private Integer deleteStatus;

    /**
     * 下单时使用的积分
     *
     * @mbggenerated
     */
    private Integer useIntegration;

    /**
     * 支付时间
     *
     * @mbggenerated
     */
    private Date paymentTime;

    /**
     * 发货时间
     *
     * @mbggenerated
     */
    private Date deliveryTime;

    /**
     * 确认收货时间
     *
     * @mbggenerated
     */
    private Date receiveTime;

    /**
     * 评价时间
     *
     * @mbggenerated
     */
    private Date commentTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(BigDecimal freightAmount) {
        this.freightAmount = freightAmount;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public BigDecimal getIntegrationAmount() {
        return integrationAmount;
    }

    public void setIntegrationAmount(BigDecimal integrationAmount) {
        this.integrationAmount = integrationAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public Integer getAutoConfirmDay() {
        return autoConfirmDay;
    }

    public void setAutoConfirmDay(Integer autoConfirmDay) {
        this.autoConfirmDay = autoConfirmDay;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public String getPromotionInfo() {
        return promotionInfo;
    }

    public void setPromotionInfo(String promotionInfo) {
        this.promotionInfo = promotionInfo;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getBillHeader() {
        return billHeader;
    }

    public void setBillHeader(String billHeader) {
        this.billHeader = billHeader;
    }

    public String getBillContent() {
        return billContent;
    }

    public void setBillContent(String billContent) {
        this.billContent = billContent;
    }

    public String getBillReceiverPhone() {
        return billReceiverPhone;
    }

    public void setBillReceiverPhone(String billReceiverPhone) {
        this.billReceiverPhone = billReceiverPhone;
    }

    public String getBillReceiverEmail() {
        return billReceiverEmail;
    }

    public void setBillReceiverEmail(String billReceiverEmail) {
        this.billReceiverEmail = billReceiverEmail;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverPostCode() {
        return receiverPostCode;
    }

    public void setReceiverPostCode(String receiverPostCode) {
        this.receiverPostCode = receiverPostCode;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverRegion() {
        return receiverRegion;
    }

    public void setReceiverRegion(String receiverRegion) {
        this.receiverRegion = receiverRegion;
    }

    public String getReceiverDetailAddress() {
        return receiverDetailAddress;
    }

    public void setReceiverDetailAddress(String receiverDetailAddress) {
        this.receiverDetailAddress = receiverDetailAddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getUseIntegration() {
        return useIntegration;
    }

    public void setUseIntegration(Integer useIntegration) {
        this.useIntegration = useIntegration;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", couponId=").append(couponId);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", createTime=").append(createTime);
        sb.append(", memberUsername=").append(memberUsername);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", freightAmount=").append(freightAmount);
        sb.append(", promotionAmount=").append(promotionAmount);
        sb.append(", integrationAmount=").append(integrationAmount);
        sb.append(", couponAmount=").append(couponAmount);
        sb.append(", discountAmount=").append(discountAmount);
        sb.append(", payType=").append(payType);
        sb.append(", sourceType=").append(sourceType);
        sb.append(", status=").append(status);
        sb.append(", orderType=").append(orderType);
        sb.append(", deliveryCompany=").append(deliveryCompany);
        sb.append(", deliverySn=").append(deliverySn);
        sb.append(", autoConfirmDay=").append(autoConfirmDay);
        sb.append(", integration=").append(integration);
        sb.append(", growth=").append(growth);
        sb.append(", promotionInfo=").append(promotionInfo);
        sb.append(", billType=").append(billType);
        sb.append(", billHeader=").append(billHeader);
        sb.append(", billContent=").append(billContent);
        sb.append(", billReceiverPhone=").append(billReceiverPhone);
        sb.append(", billReceiverEmail=").append(billReceiverEmail);
        sb.append(", receiverName=").append(receiverName);
        sb.append(", receiverPhone=").append(receiverPhone);
        sb.append(", receiverPostCode=").append(receiverPostCode);
        sb.append(", receiverProvince=").append(receiverProvince);
        sb.append(", receiverCity=").append(receiverCity);
        sb.append(", receiverRegion=").append(receiverRegion);
        sb.append(", receiverDetailAddress=").append(receiverDetailAddress);
        sb.append(", note=").append(note);
        sb.append(", confirmStatus=").append(confirmStatus);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", useIntegration=").append(useIntegration);
        sb.append(", paymentTime=").append(paymentTime);
        sb.append(", deliveryTime=").append(deliveryTime);
        sb.append(", receiveTime=").append(receiveTime);
        sb.append(", commentTime=").append(commentTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}