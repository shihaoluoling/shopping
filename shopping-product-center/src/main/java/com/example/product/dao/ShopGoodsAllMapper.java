package com.example.product.dao;

import java.util.List;

public interface ShopGoodsAllMapper {
    List<ShopGoodsAllMapper> SelectGoodsAll();
    int deleteGoodsAll(int GoodsId);
}
