package com.osd.sync.service.gas;

import com.osd.sync.entity.gas.GasAreaCommunityEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasAreaCommunityService extends IService<GasAreaCommunityEntity> {
    Map<String,Object> getListAreaToMap();

    GasAreaCommunityEntity getOnes(String name);

    boolean saves(GasAreaCommunityEntity gasAreaCommunityEntity);

    boolean updates(GasAreaCommunityEntity gasAreaCommunityEntity);
}
