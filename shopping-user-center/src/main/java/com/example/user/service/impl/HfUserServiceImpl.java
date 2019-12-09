package com.example.user.service.impl;

import com.example.user.dao.HfUserMapper;
import com.example.user.model.HfUser;
import com.example.user.service.HfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HfUserServiceImpl implements HfUserService {

    @Autowired
    private HfUserMapper hfUserMapper;

    @Override
    public Integer hfUserIsExist(String phone) {
        return hfUserMapper.hfUserIsExist(phone);
    }

    @Override
    public Integer register(String phone) {
        return hfUserMapper.register(phone);
    }

    @Override
    public Integer insert(HfUser hfUser) {
        return hfUserMapper.insert(hfUser);
    }

    @Override
    public Integer updateByPrimaryKeySelective(HfUser hfUser) {
        return hfUserMapper.updateByPrimaryKeySelective(hfUser);
    }

    @Override
    public Integer judgeUserById(int id) {
        return hfUserMapper.judgeUserById(id);
    }
}