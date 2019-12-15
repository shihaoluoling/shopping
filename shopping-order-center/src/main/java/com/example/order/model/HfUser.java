package com.example.order.model;

import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class HfUser {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ApiModelProperty(name = "Uid")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "电话号码")
    private String phone;
    @ApiModelProperty(value = "邮箱地址")
    private String email;
    @ApiModelProperty(value = "用户来源")
    private String sourceType;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    @ApiModelProperty(value = "性别0女1男")
    private Byte sex;
    @ApiModelProperty(value = "出生时间")
    private Date birthDay;
    @ApiModelProperty(value = "用户状态")
    private Byte userStatus;
    @ApiModelProperty(value = "头像地址id")
    private Integer fileId;
    @ApiModelProperty(value = "用户地址")
    private String address;
    @ApiModelProperty(value = "用户等级")
    private Byte userLevel;
    @ApiModelProperty(value = "最后一次登录时间")
    private Date lastAuthTime;
    @ApiModelProperty(value = "地区")
    private String region;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Byte idDeleted;

    private List<HfOrderLogistics> hfOrderLogisticsList;
    private List<HfOrders> hfOrdersList;
    private List<HfOrdersDetail> hfOrdersDetailList;
    private List<HfOrderStatus> hfOrderStatusList;
    private List<HfGoods> hfGoodsList;
    private List<HfUserAddress> hfUserAddressList;

    public List<HfUserAddress> getHfUserAddressList() {
        return hfUserAddressList;
    }

    public void setHfUserAddressList(List<HfUserAddress> hfUserAddressList) {
        this.hfUserAddressList = hfUserAddressList;
    }

    public List<HfGoods> getHfGoodsList() {
        return hfGoodsList;
    }

    public void setHfGoodsList(List<HfGoods> hfGoodsList) {
        this.hfGoodsList = hfGoodsList;
    }

    public List<HfOrderLogistics> getHfOrderLogisticsList() {
        return hfOrderLogisticsList;
    }

    public void setHfOrderLogisticsList(List<HfOrderLogistics> hfOrderLogisticsList) {
        this.hfOrderLogisticsList = hfOrderLogisticsList;
    }

    public List<HfOrders> getHfOrdersList() {
        return hfOrdersList;
    }

    public void setHfOrdersList(List<HfOrders> hfOrdersList) {
        this.hfOrdersList = hfOrdersList;
    }

    public List<HfOrdersDetail> getHfOrdersDetailList() {
        return hfOrdersDetailList;
    }

    public void setHfOrdersDetailList(List<HfOrdersDetail> hfOrdersDetailList) {
        this.hfOrdersDetailList = hfOrdersDetailList;
    }

    public List<HfOrderStatus> getHfOrderStatusList() {
        return hfOrderStatusList;
    }

    public void setHfOrderStatusList(List<HfOrderStatus> hfOrderStatusList) {
        this.hfOrderStatusList = hfOrderStatusList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Byte getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Byte userLevel) {
        this.userLevel = userLevel;
    }

    public Date getLastAuthTime() {
        return lastAuthTime;
    }

    public void setLastAuthTime(Date lastAuthTime) {
        this.lastAuthTime = lastAuthTime;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Byte getIdDeleted() {
        return idDeleted;
    }

    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }


    @Override
    public String toString() {
        return "HfUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", birthDay=" + birthDay +
                ", userStatus=" + userStatus +
                ", fileId=" + fileId +
                ", address='" + address + '\'' +
                ", userLevel=" + userLevel +
                ", lastAuthTime=" + lastAuthTime +
                ", region='" + region + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", idDeleted=" + idDeleted +
                ", hfOrderLogisticsList=" + hfOrderLogisticsList +
                ", hfOrdersList=" + hfOrdersList +
                ", hfOrdersDetailList=" + hfOrdersDetailList +
                ", hfOrderStatusList=" + hfOrderStatusList +
                '}';
    }
}