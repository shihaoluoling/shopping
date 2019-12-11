package com.example.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.order.dao.*;
import com.example.order.model.*;
import com.example.order.print.PrintUtil;
import com.example.order.service.OrdersService;
import com.shopping.utils.response.handler.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.shopping.utils.response.handler.ResponseEntity.BodyBuilder;
import com.shopping.utils.response.handler.ResponseUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;


@RestController("/order")
@RequestMapping
@Api
public class OrderController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private HfOrdersDetailMapper hfOrdersDetailMapper;
    @Autowired
    private HfOrderLogisticsMapper hfOrderLogisticsMapper;
    @Autowired
    private HfOrderStatusMapper hfOrderStatusMapper;
    @Autowired
    private HfOrdersMapper hfOrdersMapper;
    @Autowired
    private ShopOrderUserMapper shopOrderUserMapper;

    @CrossOrigin
    @ApiOperation(value = "查询所有订单", notes = "查询所有订单")
    @RequestMapping(value = "/selectOrderAll", method = RequestMethod.PUT)
    public List<HfUser> selectOrderAll() {
        return ordersService.selectOrderAll();
    }

    @SuppressWarnings("rawtypes")
    @CrossOrigin
    @ApiOperation(value = "创建订单", notes = "创建订单")
    @RequestMapping(value = "/creat", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> creatOrder(HfOrdersDetail hfOrdersDetail, HfOrders hfOrder, HfOrderLogistics hfOrderLogistics,HfUser hfUser)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        int hfUserID= ordersService.saveUser(hfUser);
        System.out.println(hfUserID);
        hfOrder.setUserId(hfUserID);
        hfOrdersMapper.insert(hfOrder);
        int hfOrderID=hfOrder.getId();
        hfOrderLogistics.setUserId(hfUserID);
        hfOrderLogistics.setOrdersId(hfOrderID);
        hfOrdersDetail.setOrdersId(hfOrderID);
        /*----*/
        System.out.println(hfOrdersDetail);
        hfOrdersDetailMapper.insert(hfOrdersDetail);
        int hfOrdersDetailID=hfOrdersDetail.getId();
        hfOrderLogistics.setOrderDetailId(hfOrdersDetailID);
        /*设置与detail表相同的属性*/
        hfOrderLogistics.setCreateTime(hfOrdersDetail.getCreateTime());
        hfOrderLogistics.setOrdersId(hfOrdersDetail.getOrdersId());
        hfOrderLogistics.setGoogsId(hfOrdersDetail.getGoogsId());
        hfOrderLogistics.setRespId(hfOrdersDetail.getRespId());
        hfOrderLogistics.setHfDesc(hfOrdersDetail.getHfDesc());
        hfOrderLogistics.setModifyTime(hfOrdersDetail.getModifyTime());
        hfOrderLogistics.setModifyTime(hfOrdersDetail.getModifyTime());
        hfOrderLogistics.setIsDeleted(hfOrdersDetail.getIsDeleted());

        hfOrderLogisticsMapper.insert(hfOrderLogistics);

        return null;
    }

    @SuppressWarnings("rawtypes")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "hfName", value = "订单状态", required = true, type = "VARCHAR"),
    })
    @CrossOrigin
    @ApiOperation(value = "筛选订单状态", notes = "筛选订单状态")
    @RequestMapping(value = "/selectOrderDetailStatus", method = RequestMethod.GET)
    public List<HfUser> selectOrderDetailStatus(HfOrderStatus hfOrderStatus)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return ordersService.selectOneDZF(hfOrderStatus);
    }

    @CrossOrigin
    @ApiOperation(value = "订单状态", notes = "订单状态")
    @RequestMapping(value = "/selectStatus", method = RequestMethod.GET)
    public List<HfOrderStatus> selectStatus()
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return ordersService.selectStatus();
    }

    @CrossOrigin
    @ApiOperation(value = "修改订单状态", notes = "修改订单状态")
    @RequestMapping(value = "/UpdateDetail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "状态id", required = true, type = "Integer")
    })
    public List<HfOrderStatus> UpdateDetail(@RequestParam Integer orderId,  @RequestParam Integer id)
            throws JSONException {

        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfOrdersDetail hfOrdersDetail = hfOrdersDetailMapper.selectByPrimaryKey(orderId);
        HfOrderStatus hfOrderStatus = hfOrderStatusMapper.selectByPrimaryKey(id);
        hfOrdersDetail.setOrderDetailStatus(hfOrderStatus.getHfName());
        hfOrdersDetailMapper.updateByPrimaryKey(hfOrdersDetail);
        return null;
    }

    @CrossOrigin
    @ApiOperation(value = "订单详情用户id", notes = "用户id")
    @RequestMapping(value = "/selectDetailsOrderOne", method = RequestMethod.GET)
    public List<HfUser> selectDetailsOrderOne(int id)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return ordersService.selectDetailsOrderOne(id);
    }


    @CrossOrigin
    @ApiOperation(value = "修改订单", notes = "修改订单")
    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "订单id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> updateUser(HfOrdersDetail hfOrdersDetail, HfOrders hfOrder, HfOrderLogistics hfOrderLogistics,HfUser hfUser)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (hfOrdersDetail== null||hfOrder==null||hfOrderLogistics==null||hfUser==null){
            throw new Exception("请输入");
        }else {
            ordersService.updateUser(hfOrder);
            HfOrders HfOrders3= hfOrdersMapper.selectByPrimaryKey(hfOrder.getId());


            hfUser.setCreateDate(LocalDateTime.now());
            hfUser.setModifyDate(LocalDateTime.now());
            System.out.println(HfOrders3.getUserId()+"userID");
            hfOrderLogistics.setCreateTime(LocalDateTime.now());
            hfOrderLogistics.setModifyTime(LocalDateTime.now());
            hfUser.setId(HfOrders3.getUserId());
            shopOrderUserMapper.updateByPrimaryKeySelective(hfUser);

            hfOrderLogistics.setCreateTime(LocalDateTime.now());
            hfOrderLogistics.setModifyTime(LocalDateTime.now());
            Example example = new Example(HfOrderLogistics.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("ordersId", hfOrder.getId());
            hfOrderLogisticsMapper.updateByExampleSelective(hfOrderLogistics, example);
            System.out.println(hfOrderLogistics.getId() + "---" + hfOrderLogistics.getUserId());


            hfOrdersDetail.setCreateTime(LocalDateTime.now());
            hfOrdersDetail.setModifyTime(LocalDateTime.now());
            Example example1 = new Example(HfOrdersDetail.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("ordersId", hfOrder.getId());
            hfOrdersDetailMapper.updateByExampleSelective(hfOrdersDetail, example1);
            System.out.println(hfOrdersDetail.getId());
        }
        return null;
    }

    @CrossOrigin
    @ApiOperation(value = "打印订单", notes = "打印订单")
    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public List<HfUser> print()
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        PrintUtil printUtil = new PrintUtil();
        printUtil.area.append("成功");
        return null;
    }

    @CrossOrigin
    @ApiOperation(value = "模糊查询", notes = "模糊查询")
    @RequestMapping(value = "/OrderOne", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectOrderOne(OneOrder oneOrder)
            throws JSONException {
        System.out.println(oneOrder.getHfName());
        System.out.println(ordersService.selectOrderOne(oneOrder));
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(ordersService.selectOrderOne(oneOrder)));
    }
    @CrossOrigin
    @ApiOperation(value = "今天的订单", notes = "今天的订单")
    @RequestMapping(value = "/OrdertoDay", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> OrdertoDay()
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(ordersService.OrdertoDay()));
    }
    @CrossOrigin
    @ApiOperation(value = "昨天的订单", notes = "昨天的订单")
    @RequestMapping(value = "/OrderYesterday", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> OrderYesterday()
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(ordersService.OrderYesterday()));
    }
}
