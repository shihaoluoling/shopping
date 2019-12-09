package com.example.order.dao;

import com.example.order.model.HfOrderStatus;
import com.example.order.model.HfUser;
import com.example.order.model.OneOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ShopOrderSelectMapper extends Mapper<HfUser> {
    List<HfUser> selectOrderAll();
    List<HfUser> selectOneDZF(HfOrderStatus hfOrderStatus);
    List<HfUser> selectOrder(Integer orderId,String hfName,String payMethodType,String orderDetailStatus);
    List<HfUser> selectDetailsOrderOne(@Param("id")int id);
    List<HfUser> selectOrderOne(OneOrder oneOrder);
    List<HfUser> OrdertoDay();
    List<HfUser>OrderYesterday();
}
