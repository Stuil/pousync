package com.osd.sync.mapper.mydb;

import com.osd.sync.entity.mydb.UsercardinfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户卡信息 Mapper 接口
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */

public interface UsercardinfoMapper extends BaseMapper<UsercardinfoEntity> {

    List<UsercardinfoEntity> selectCard();

}
