package com.osd.sync.service;

import com.osd.sync.mapper.UserCardMapper;
import com.osd.sync.model.UserCardModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @title: UserCardService
 * @description: 用户、卡服务类
 * @date: 2020/8/13
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */
@org.springframework.stereotype.Service
public class UserCardService {
    @Autowired
    UserCardMapper userCardMapper;

    public List<UserCardModel> userCardModels(){
        return userCardMapper.userCardModels();
    }
}
