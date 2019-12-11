package com.example.product.model;

import io.swagger.annotations.ApiModelProperty;

public class AlterGoods {
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "类目名称")
    private String hfCategoryName;
    @ApiModelProperty(value = "品牌名称")
    private String brandName;
    @ApiModelProperty(value = "类目id")
    private Integer categoryId;
    @ApiModelProperty(value = "品牌id")
    private Integer brandId;
    @ApiModelProperty(value = "库存id")
    private Integer respId;
    @ApiModelProperty(value = "价格id")
    private Integer priceId;
    @ApiModelProperty(value = "商品描述")
    private String goodsDesc;
    @ApiModelProperty(value = "品牌类型")
    private String brandType;
    @ApiModelProperty(value = "品牌描述")
    private String hfDesc;
    @ApiModelProperty(value = "商品id")
    private Integer googsId;
    @ApiModelProperty(value = "仓库id")
    private Integer warehouseId;
    @ApiModelProperty(value = "库存量")
    private String quantity;
    private Integer hfStatus;
    @ApiModelProperty(value = "库存描述")
    private String respDesc;
    @ApiModelProperty(value = "修改人")
    private String lastModifier;
    @ApiModelProperty(value = "售卖价格")
    private Integer sellPrice;
    @ApiModelProperty(value = "价格描述")
    private  String priceDesc;
    @ApiModelProperty(value = "商品规格")
    private String hfValue;
    @ApiModelProperty(value = "是否会员0否1是")
    private int stoneId;

    public int getStoneId() {
        return stoneId;
    }

    public void setStoneId(int stoneId) {
        this.stoneId = stoneId;
    }

    public String getHfValue() {
        return hfValue;
    }

    public void setHfValue(String hfValue) {
        this.hfValue = hfValue;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getHfCategoryName() {
        return hfCategoryName;
    }

    public void setHfCategoryName(String hfCategoryName) {
        this.hfCategoryName = hfCategoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getRespId() {
        return respId;
    }

    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }

    public Integer getGoogsId() {
        return googsId;
    }

    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Integer getHfStatus() {
        return hfStatus;
    }

    public void setHfStatus(Integer hfStatus) {
        this.hfStatus = hfStatus;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }
}
