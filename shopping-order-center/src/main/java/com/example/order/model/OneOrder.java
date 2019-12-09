package com.example.order.model;

public class OneOrder {
    private int ordersId;
    private String hfName;
    private int payMethodType;
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
