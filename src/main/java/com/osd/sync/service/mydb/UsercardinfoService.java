package com.osd.sync.service.mydb;

import com.osd.sync.entity.mydb.UsercardinfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户卡信息 服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface UsercardinfoService extends IService<UsercardinfoEntity> {

    List<UsercardinfoEntity> selectCard();
}
