package com.example.order.model;

import io.swagger.annotations.ApiModelProperty;

public class OneOrder {
    @ApiModelProperty(value = "订单id")
    private int ordersId;
    @ApiModelProperty(value = "商品名称")
    private String hfName;
    @ApiModelProperty(value = "支付方式类型")
    private int payMethodType;
    @ApiModelProperty(value = "订单详情状态")
    private String orderDetailStatus;

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public int getPayMethodType() {
        return payMethodType;
    }

    public void setPayMethodType(int payMethodType) {
        this.payMethodType = payMethodType;
    }

    public String getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(String orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }
}
