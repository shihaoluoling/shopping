package com.example.user.dao;

import com.example.user.model.HfUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface HfUserMapper {

    Integer insert(HfUser record);

    Integer hfUserIsExist(String phone);

    Integer register(String phone);

    Integer updateByPrimaryKeySelective(HfUser record);

    Integer judgeUserById(int id);//返回用户的状态 判断用户是不是会员
}