package com.example.user.service;

import com.example.user.model.HfUser;

public interface HfUserService {

    Integer hfUserIsExist(String phone);//判断注册的用户是否存在

    Integer register(String phone);//注册

    Integer insert(HfUser hfUser);

    Integer updateByPrimaryKeySelective(HfUser hfUser);

    Integer judgeUserById(int id);//返回用户的状态 判断用户是不是会员
}