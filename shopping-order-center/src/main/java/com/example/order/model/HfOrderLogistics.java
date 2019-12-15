package com.example.order.model;

import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class HfOrderLogistics {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ApiModelProperty(name = "Lid")
    private Integer id;

    @ApiModelProperty(value = "订单id")
    private Integer ordersId;
    @ApiModelProperty(value = "订单详情id")
    private Integer orderDetailId;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户地址id")
    private Integer userAddressId;
    @ApiModelProperty(name = "googsIdLogistics",value = "物品id")
    private Integer googsId;
    @ApiModelProperty(value = "物流名称")
    private String logisticsOrderName;

    @ApiModelProperty(name = "respIdLogistics",value = "仓库id")
    private Integer respId;
    @ApiModelProperty(value = "物流订单号")
    private String logisticsOrdersId;
    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;
    @ApiModelProperty(name = "hfDescLogistics",value = "物流描述")
    private String hfDesc;

    @ApiModelProperty(name = "createTimeLogistics")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "modifyTimeLogistics")
    private LocalDateTime modifyTime;

    @ApiModelProperty(name = "lastModifierLogistics",value = "最后一次修改人")
    private String lastModifier;

    @ApiModelProperty(name = "idDeletedLogistics")
    private Short isDeleted;
    @ApiModelProperty(value = "取货方式id")
    private Integer claimId;

    public Integer getClaimId() {
        return claimId;
    }

    public void setClaimId(Integer claimId) {
        this.claimId = claimId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    public Integer getGoogsId() {
        return googsId;
    }

    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    public String getLogisticsOrderName() {
        return logisticsOrderName;
    }

    public void setLogisticsOrderName(String logisticsOrderName) {
        this.logisticsOrderName = logisticsOrderName == null ? null : logisticsOrderName.trim();
    }

    public Integer getRespId() {
        return respId;
    }

    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    public String getLogisticsOrdersId() {
        return logisticsOrdersId;
    }

    public void setLogisticsOrdersId(String logisticsOrdersId) {
        this.logisticsOrdersId = logisticsOrdersId == null ? null : logisticsOrdersId.trim();
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}