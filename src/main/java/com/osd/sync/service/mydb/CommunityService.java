package com.osd.sync.service.mydb;

import com.osd.sync.entity.mydb.CommunityEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 小区表 服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface CommunityService extends IService<CommunityEntity> {
    Map<Integer,String> getOneCommunity();
}
