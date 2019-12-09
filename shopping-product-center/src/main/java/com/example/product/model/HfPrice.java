package com.example.product.model;

import java.util.Date;

public class HfPrice {
    private Integer id;

    private Integer googsId;

    private Integer priceModeId;

    private Integer sellPrice;

    private Short isUsePriceMode;

    private String hfDesc;

    private Date createTime;

    private Date modifyTime;

    private String lastModifier;

    private Short isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoogsId() {
        return googsId;
    }

    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    public Integer getPriceModeId() {
        return priceModeId;
    }

    public void setPriceModeId(Integer priceModeId) {
        this.priceModeId = priceModeId;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Short getIsUsePriceMode() {
        return isUsePriceMode;
    }

    public void setIsUsePriceMode(Short isUsePriceMode) {
        this.isUsePriceMode = isUsePriceMode;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}