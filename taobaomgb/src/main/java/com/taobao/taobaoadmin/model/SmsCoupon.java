package com.taobao.taobaoadmin.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SmsCoupon implements Serializable {
    private Long id;

    /**
     * 优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券
     *
     * @mbggenerated
     */
    private Integer type;

    private String name;

    /**
     * 使用平台：0->全部；1->移动；2->PC
     *
     * @mbggenerated
     */
    private Integer platform;

    /**
     * 数量
     *
     * @mbggenerated
     */
    private Integer count;

    /**
     * 金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 每人限领张数
     *
     * @mbggenerated
     */
    private Integer perLimit;

    /**
     * 使用门槛；0表示无门槛
     *
     * @mbggenerated
     */
    private BigDecimal minPoint;

    private Date startTime;

    private Date endTime;

    /**
     * 使用类型：0->全场通用；1->指定分类；2->指定商品
     *
     * @mbggenerated
     */
    private Integer useType;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String note;

    /**
     * 发行数量
     *
     * @mbggenerated
     */
    private Integer publishCount;

    /**
     * 已使用数量
     *
     * @mbggenerated
     */
    private Integer useCount;

    /**
     * 领取数量
     *
     * @mbggenerated
     */
    private Integer receiveCount;

    /**
     * 可以领取的日期
     *
     * @mbggenerated
     */
    private Date enableTime;

    /**
     * 优惠码
     *
     * @mbggenerated
     */
    private String code;

    /**
     * 可领取的会员类型：0->无限时
     *
     * @mbggenerated
     */
    private Integer memberLevel;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPerLimit() {
        return perLimit;
    }

    public void setPerLimit(Integer perLimit) {
        this.perLimit = perLimit;
    }

    public BigDecimal getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(BigDecimal minPoint) {
        this.minPoint = minPoint;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getPublishCount() {
        return publishCount;
    }

    public void setPublishCount(Integer publishCount) {
        this.publishCount = publishCount;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Integer getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(Integer receiveCount) {
        this.receiveCount = receiveCount;
    }

    public Date getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", name=").append(name);
        sb.append(", platform=").append(platform);
        sb.append(", count=").append(count);
        sb.append(", amount=").append(amount);
        sb.append(", perLimit=").append(perLimit);
        sb.append(", minPoint=").append(minPoint);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", useType=").append(useType);
        sb.append(", note=").append(note);
        sb.append(", publishCount=").append(publishCount);
        sb.append(", useCount=").append(useCount);
        sb.append(", receiveCount=").append(receiveCount);
        sb.append(", enableTime=").append(enableTime);
        sb.append(", code=").append(code);
        sb.append(", memberLevel=").append(memberLevel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}