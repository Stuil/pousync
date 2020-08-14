package com.osd.sync.mapper;

import com.osd.sync.model.UserCardModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @title: UserCardMapper
 * @description:
 * @date: 2020/8/13
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */
@Mapper
public interface UserCardMapper {
    List<UserCardModel> userCardModels();
}
