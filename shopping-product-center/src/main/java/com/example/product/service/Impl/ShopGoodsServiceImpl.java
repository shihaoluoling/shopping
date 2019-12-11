package com.example.product.service.Impl;

import com.example.product.dao.ShopBrandMapper;
import com.example.product.dao.ShopCategoryMapper;
import com.example.product.dao.ShopGoodsAllMapper;
import com.example.product.dao.ShopGoodsMapper;
import com.example.product.model.HfBrand;
import com.example.product.model.HfCategory;
import com.example.product.model.HfGoods;
import com.example.product.model.ShoppingGoodsAll;
import com.example.product.service.ShopGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ShopGoodsServiceImpl implements ShopGoodsService {
    @Autowired
    private ShopGoodsMapper shopGoodsMapper;
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;
    @Autowired
    private ShopBrandMapper shopBrandMapper;
    @Autowired
    private ShopGoodsAllMapper shopGoodsAllMapper;

    @Override
    public List<HfGoods> selectGoods() {
        return shopGoodsMapper.selectAll();
    }
//品牌查询
    @Override
    public List<HfBrand> selectBrand() {
        return shopBrandMapper.selectAll();
    }

    //类目查询
    @Override
    public List<HfCategory> selectCategory() {
        return shopCategoryMapper.selectAll();
    }

    //商品基本信息添加
    @Override
    public int basicGoods(HfGoods hfGoods) {
        hfGoods.setCreateTime(LocalDateTime.now());
        hfGoods.setModifyTime(LocalDateTime.now());
        hfGoods.setIsDeleted((short) 0);
        shopGoodsMapper.insert(hfGoods);
        int goodsId = hfGoods.getId();
        System.out.println(goodsId);
        return goodsId;
    }

    @Override
    public List<ShopGoodsAllMapper> SelectGoodsAll() {
        return shopGoodsAllMapper.SelectGoodsAll();
    }

    @Override
    public int deleteGoodsAll(int GoodsId) {
        return shopGoodsAllMapper.deleteGoodsAll(GoodsId);
    }
}
