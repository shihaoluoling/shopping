package com.example.order.service;

import com.example.order.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface OrdersService {
    List<HfOrderLogistics> test();
    List<HfUser> selectOrder();
    List<HfUser>selectOrderAll();
    List<HfUser> selectOneDZF(HfOrderStatus hfOrderStatus);
    int saveUser(HfUser hfUser);
    List<HfOrderStatus> selectStatus();
    List<HfUser> selectDetailsOrderOne(int id);
    int updateUser(HfOrders hfOrder);
    List<HfUser> selectOrderOne(OneOrder oneOrder);
    List<HfUser> OrdertoDay();
    List<HfUser>OrderYesterday();
    List<HfUser> orderHebdomad();
    //本月
    List<HfUser> selectMonth();
    //时间段
    List<HfUser> SelectTime(Date createTime, Date createTime2);

    Cancel cancel(int id);
}
