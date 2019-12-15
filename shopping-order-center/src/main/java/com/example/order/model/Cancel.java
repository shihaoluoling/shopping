package com.example.order.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class Cancel {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String cancel_name;
    private Integer goods_id;
    private String cancel_root;
    private LocalDateTime create_time;
    private  LocalDateTime modify_time;
    private Short isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCancel_name() {
        return cancel_name;
    }

    public void setCancel_name(String cancel_name) {
        this.cancel_name = cancel_name;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getCancel_root() {
        return cancel_root;
    }

    public void setCancel_root(String cancel_root) {
        this.cancel_root = cancel_root;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public LocalDateTime getModify_time() {
        return modify_time;
    }

    public void setModify_time(LocalDateTime modify_time) {
        this.modify_time = modify_time;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}
