package com.example.product.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

public class HfGoodsSpec {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private Integer goodsId;

    private String hfSpecId;

    private Integer categorySpecId;

    private String hfValue;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private String lastModifier;

    private Short isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getHfSpecId() {
        return hfSpecId;
    }

    public void setHfSpecId(String hfSpecId) {
        this.hfSpecId = hfSpecId == null ? null : hfSpecId.trim();
    }

    public Integer getCategorySpecId() {
        return categorySpecId;
    }

    public void setCategorySpecId(Integer categorySpecId) {
        this.categorySpecId = categorySpecId;
    }

    public String getHfValue() {
        return hfValue;
    }

    public void setHfValue(String hfValue) {
        this.hfValue = hfValue == null ? null : hfValue.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
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