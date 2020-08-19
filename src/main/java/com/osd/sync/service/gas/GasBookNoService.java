package com.osd.sync.service.gas;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.osd.sync.entity.gas.GasAreaCommunityEntity;
import com.osd.sync.entity.gas.GasBookNoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasBookNoService extends IService<GasBookNoEntity> {

    GasBookNoEntity getBooks(String communityId);

    GasBookNoEntity getOnes(Wrapper<GasBookNoEntity> queryWrapper);

    boolean saves(GasBookNoEntity bookNoEntity);

    boolean updates(GasBookNoEntity bookNoEntity);
}
