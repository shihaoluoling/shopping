package com.example.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.order.Test.QRCodeUtils;
import com.example.order.dao.*;
import com.example.order.model.*;
import com.example.order.print.PrintUtil;
import com.example.order.service.OrdersService;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.shopping.utils.response.handler.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.shopping.utils.response.handler.ResponseEntity.BodyBuilder;
import com.shopping.utils.response.handler.ResponseUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Autowired
    private ShopClaimMapper shopClaimMapper;
    @Autowired
    private CancelMapper cancelMapper;
    @Autowired
    private HfGoodsMapper hfGoodsMapper;
    @CrossOrigin
    @ApiOperation(value = "查询所有订单", notes = "查询所有订单")
    @RequestMapping(value = "/selectOrderAll", method = RequestMethod.PUT)
    public List<HfUser> selectOrderAll() {
        return ordersService.selectOrderAll();
    }

    @SuppressWarnings("rawtypes")
    @CrossOrigin
    @ApiOperation(value = "测试创建订单", notes = "测试创建订单")
    @RequestMapping(value = "/creat", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名称", required = true, type = "VARCHAR")

    })
    public ResponseEntity<JSONObject> creatOrder(HfOrdersDetail hfOrdersDetail, HfOrders hfOrder, HfOrderLogistics hfOrderLogistics,HfUser hfUser)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        int hfUserID= ordersService.saveUser(hfUser);
        System.out.println(hfUserID);
        hfOrder.setUserId(hfUserID);
        hfOrder.setCreateTime(LocalDateTime.now());
        hfOrder.setModifyTime(LocalDateTime.now());
        hfOrder.setIsDeleted((short) 0);
        hfOrder.setAmount(hfOrdersDetail.getPurchaseQuantity()*hfOrdersDetail.getPurchasePrice());
        hfOrdersMapper.insert(hfOrder);
        int hfOrderID=hfOrder.getId();
        hfOrderLogistics.setUserId(hfUserID);
        hfOrderLogistics.setOrdersId(hfOrderID);
        hfOrdersDetail.setOrdersId(hfOrderID);
        /*----*/
        System.out.println(hfOrdersDetail);
        hfOrdersDetail.setCreateTime(LocalDateTime.now());
        hfOrdersDetail.setModifyTime(LocalDateTime.now());
        hfOrdersDetail.setIsDeleted((short) 0);
        hfOrdersDetailMapper.insert(hfOrdersDetail);
        int hfOrdersDetailID=hfOrdersDetail.getId();
        hfOrderLogistics.setOrderDetailId(hfOrdersDetailID);
        /*设置与detail表相同的属性*/
        hfOrderLogistics.setCreateTime(LocalDateTime.now());
        hfOrderLogistics.setOrdersId(hfOrdersDetail.getOrdersId());
        hfOrderLogistics.setGoogsId(hfOrdersDetail.getGoogsId());
        hfOrderLogistics.setRespId(hfOrdersDetail.getRespId());
        hfOrderLogistics.setHfDesc(hfOrdersDetail.getHfDesc());
        hfOrderLogistics.setModifyTime(LocalDateTime.now());
        hfOrderLogistics.setIsDeleted((short) 0);

        hfOrderLogisticsMapper.insert(hfOrderLogistics);

        return builder.body(ResponseUtils.getResponseBody(hfUser));
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
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "hfOrderLogisticsId", required = true, type = "Integer"),
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
    @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, type = "Integer")
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
        System.out.println(hfOrder.getId());
        System.out.println(hfOrderLogistics.getClaimId());
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (hfOrdersDetail== null||hfOrder==null||hfOrderLogistics==null||hfUser==null){
            throw new Exception("请输入");
        }else {
            hfOrder.setCreateTime(LocalDateTime.now());
            hfOrder.setModifyTime(LocalDateTime.now());
            hfOrder.setIsDeleted((short) 0);
            ordersService.updateUser(hfOrder);
            HfOrders HfOrders3= hfOrdersMapper.selectByPrimaryKey(hfOrder.getId());


            hfUser.setCreateDate(LocalDateTime.now());
            hfUser.setModifyDate(LocalDateTime.now());
            System.out.println(HfOrders3.getUserId()+"userID");
            hfOrderLogistics.setCreateTime(LocalDateTime.now());
            hfOrderLogistics.setModifyTime(LocalDateTime.now());
            hfOrderLogistics.setClaimId(hfOrderLogistics.getClaimId());
            hfOrderLogistics.setIsDeleted((short) 0);
            hfUser.setId(HfOrders3.getUserId());
            shopOrderUserMapper.updateByPrimaryKeySelective(hfUser);
            Example example = new Example(HfOrderLogistics.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("ordersId", hfOrder.getId());
            hfOrderLogisticsMapper.updateByExampleSelective(hfOrderLogistics, example);
            System.out.println(hfOrderLogistics.getId() + "---" + hfOrderLogistics.getUserId()+"------"+hfOrderLogistics.getClaimId());


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
    @CrossOrigin
    @ApiOperation(value = "七天内的订单", notes = "七天内的订单")
    @RequestMapping(value = "/orderHebdomad", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> orderHebdomad()
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(ordersService.orderHebdomad()));
    }
    @CrossOrigin
    @ApiOperation(value = "30天的订单", notes = "本月的订单")
    @RequestMapping(value = "/selectMonth", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectMonth()
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(ordersService.selectMonth()));
    }
    @CrossOrigin
    @ApiOperation(value = "时间段筛选", notes = "时间段筛选")
    @RequestMapping(value = "/LocalDateTime", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> LocalDateTime(@RequestParam("createTime")Date createTime,@RequestParam("createTime2") Date createTime2)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(ordersService.SelectTime(createTime,createTime2)));
    }
    @CrossOrigin
    @ApiOperation(value = "取货方式查询", notes = "取货方式查询")
    @RequestMapping(value = "/selectClaim", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectClaim() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(shopClaimMapper.selectAll()));
    }
    @CrossOrigin
    @ApiOperation(value = "用户创建订单", notes = "用户创建订单")
    @RequestMapping(value = "/saveOrder", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "payStatus", value = "支付状态", required = true, type = "VARCHAR"),
            @ApiImplicitParam(paramType = "query", name = "orderType", value = "订单类型", required = true, type = "VARCHAR"),
    })
    public ResponseEntity<JSONObject> saveOrder(HfOrders hfOrders,HfOrderLogistics hfOrderLogistics,HfOrdersDetail hfOrdersDetail) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfOrders.setCreateTime(LocalDateTime.now());
        hfOrders.setModifyTime(LocalDateTime.now());
        hfOrders.setIsDeleted((short) 0);
        hfOrdersMapper.insert(hfOrders);
        int ordersid =hfOrders.getId();
                hfOrderLogistics.setOrdersId(ordersid);
        hfOrderLogistics.setIsDeleted((short) 0);
        hfOrderLogistics.setCreateTime(LocalDateTime.now());
        hfOrderLogistics.setModifyTime(LocalDateTime.now());
        hfOrderLogisticsMapper.insert(hfOrderLogistics);
        hfOrdersDetail.setCreateTime(LocalDateTime.now());
        hfOrdersDetail.setModifyTime(LocalDateTime.now());
        hfOrdersDetail.setIsDeleted((short) 0);
        hfOrdersDetail.setOrdersId(ordersid);
        hfOrdersDetailMapper.insert(hfOrdersDetail);

        return builder.body(ResponseUtils.getResponseBody(hfOrders));
    }


    /**
     *  生成二维码
     * @param type 二维码的类型，为了演示效果，1跳百度的，2是跳京东

     * */
    @GetMapping(value = "/activity/create/activity-code")
    @ApiOperation("生成活动详情二维码")
    public void getCode(Integer hid ,Integer goodsId ,HttpServletResponse response) throws Exception {

        HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goodsId);
        if (hfGoods==null){
            throw new Exception("商品不存在");
        }if (hid==null){
            throw new Exception("审核员不存在");
        }
       int cancel= hfGoods.getCancelId();
        System.out.println(cancel);
       if (cancel==hid){
          int type = 1;
           // 设置响应流信息
           response.setContentType("image/jpg");
           response.setHeader("Pragma", "no-cache");
           response.setHeader("Cache-Control", "no-cache");
           response.setDateHeader("Expires", 0);
           OutputStream stream = response.getOutputStream();
           //type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
           String content = (type == 1 ? "核销成功" : "核销失败");
           //获取一个二维码图片
           BitMatrix bitMatrix = QRCodeUtils.createCode(content);
           //以流的形式输出到前端
           MatrixToImageWriter.writeToStream(bitMatrix , "jpg" , stream);
           System.out.println(content);
           if (content=="核销成功"){
               HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
               hfOrdersDetail.setOrderDetailStatus("已完成");
               Example example = new Example(HfOrdersDetail.class);
               Example.Criteria criteria = example.createCriteria();
               criteria.andEqualTo("googsId",goodsId);
               hfOrdersDetailMapper.updateByExampleSelective(hfOrdersDetail,example);
           }else {
               throw new Exception("未扫描");
           }

       }else {
          int type = 2;
           // 设置响应流信息
           response.setContentType("image/jpg");
           response.setHeader("Pragma", "no-cache");
           response.setHeader("Cache-Control", "no-cache");
           response.setDateHeader("Expires", 0);
           OutputStream stream = response.getOutputStream();
           //type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
           String content = (type == 1 ? "核销成功" : "核销失败,请商品对应的管理员核销");
           //获取一个二维码图片
           BitMatrix bitMatrix = QRCodeUtils.createCode(content);
           //以流的形式输出到前端
           MatrixToImageWriter.writeToStream(bitMatrix , "jpg" , stream);

           HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
           hfOrdersDetail.setOrderDetailStatus("核销失败");
           Example example = new Example(HfOrdersDetail.class);
           Example.Criteria criteria = example.createCriteria();
           criteria.andEqualTo("googsId",goodsId);
           hfOrdersDetailMapper.updateByExampleSelective(hfOrdersDetail,example);
       }

    }

    @GetMapping(value = "/activity/create/activity-code1")
    @ApiOperation("生成活动详情二维码123")
    public int getCode1(Integer goodsId ,HttpServletResponse response) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goodsId);

        if (goodsId==null){
            throw new Exception("请选择核销商品");
        }if (hfGoods==null){
            throw new Exception("商品不存在");
        }if (hfGoods.getCancelId()==null){
            throw new Exception("商品无核销员");
        }
            int type = 1;
        String abc = String.valueOf(hfGoods.getCancelId());
            // 设置响应流信息
            response.setContentType("image/jpg");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            OutputStream stream = response.getOutputStream();
            //type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
            String content = (type == 1 ? "http://localhost:9901/selectMonth" : "核销失败");
            //获取一个二维码图片
            BitMatrix bitMatrix = QRCodeUtils.createCode(content);
            //以流的形式输出到前端
            MatrixToImageWriter.writeToStream(bitMatrix , "jpg" , stream);

        return hfGoods.getCancelId();
    }
}
