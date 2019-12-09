package com.example.order.service.Impl;

import com.example.order.dao.*;
import com.example.order.model.*;
import com.example.order.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    private int a;


    @Autowired
    HfOrderLogisticsMapper hfOrderLogisticsMapper;
    @Autowired
    ShopOrderSelectMapper shopOrderSelect;
    @Autowired
    HfOrdersMapper hfOrdersMapper;
    @Autowired
    HfOrdersDetailMapper hfOrdersDetailMapper;
    @Autowired
    HfOrderStatusMapper hfOrderStatusMapper;
    @Autowired
    ShopOrderUserMapper shopOrderUserMapper;


    @Override
    public List<HfOrderLogistics> test() {
        return hfOrderLogisticsMapper.selectAll();
    }

    @Override
    public List<HfUser> selectOrder() {

        return shopOrderSelect.selectAll();
    }
/*所有order*/
    @Override
    public List<HfUser> selectOrderAll() {
        return shopOrderSelect.selectOrderAll();
    }

    @Override
    public List<HfUser> selectOneDZF(HfOrderStatus hfOrderStatus) {
        return shopOrderSelect.selectOneDZF(hfOrderStatus);
    }

    /*创建订单*/
    @Override
    public int saveUser(HfUser hfUser) {
        shopOrderUserMapper.insert(hfUser);
        a = hfUser.getId();
        System.out.println(a);
        return a;
    }
/*订单状态*/
    @Override
    public List<HfOrderStatus> selectStatus() {
        return hfOrderStatusMapper.selectAll();
    }


    @Override
    public List<HfUser> selectDetailsOrderOne(int id) {
        return shopOrderSelect.selectDetailsOrderOne(id);
    }
/*修改订单*/
@Override
public int updateUser(HfOrders hfOrder) {
    hfOrder.setCreateTime(LocalDateTime.now());
    hfOrder.setModifyTime(LocalDateTime.now());
    System.out.println(LocalDateTime.now());
    return hfOrdersMapper.updateByPrimaryKeySelective(hfOrder);
}

    @Override
    public List<HfUser> selectOrderOne(OneOrder oneOrder) {

        return shopOrderSelect.selectOrderOne(oneOrder);
    }

    @Override
    public List<HfUser> OrdertoDay() {
        return shopOrderSelect.OrdertoDay();
    }

    @Override
    public List<HfUser> OrderYesterday() {
        return shopOrderSelect.OrderYesterday();
    }

}
