package com.osd.sync.service.gas;

import com.osd.sync.entity.gas.GasMeterEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasMeterService extends IService<GasMeterEntity> {
    boolean insertMeter(GasMeterEntity gasMeterEntity);
}
