package com.example.order.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class GoodsClaim {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String way;
    private LocalDateTime create_time;
    private LocalDateTime modify_time;
    private Short is_deleted;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
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

    public Short getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Short is_deleted) {
        this.is_deleted = is_deleted;
    }
}
