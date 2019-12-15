package com.example.order.dao;

import com.example.order.model.Cancel;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface CancelMapper extends Mapper<Cancel> {
    Cancel cancel(int id);
}
