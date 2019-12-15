package com.example.product.dao;

import com.example.product.model.ShoppingGoodsAll;

import java.util.List;

public interface ShopGoodsAllMapper {
    List<ShopGoodsAllMapper> SelectGoodsAll();
    int deleteGoodsAll(int GoodsId);
    List<ShoppingGoodsAll> selectCategory(int CategoryId);
    int selectQuantity();
    List<ShoppingGoodsAll> orderParticulars(int goodsId);
}
