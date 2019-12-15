package com.example.product.service;

import com.example.product.dao.ShopGoodsAllMapper;
import com.example.product.model.HfBrand;
import com.example.product.model.HfCategory;
import com.example.product.model.HfGoods;
import com.example.product.model.ShoppingGoodsAll;

import java.util.List;

public interface ShopGoodsService {
    List<HfGoods> selectGoods();
    //品牌查询
    List<HfBrand> selectBrand();
    //类目查询
    List<HfCategory> selectCategory();
    //商品基本信息
    int basicGoods(HfGoods hfGoods);
    //获取全部商品信息
    List<ShopGoodsAllMapper> SelectGoodsAll();
    //删除商品信息
    int deleteGoodsAll(int GoodsId);
//类别查询
    List<ShoppingGoodsAll> selectCategory(int CategoryId);
//商品数量查询
    int selectQuantity();
//单个商品详情
    List<ShoppingGoodsAll> orderParticulars(int goodsId);
}
