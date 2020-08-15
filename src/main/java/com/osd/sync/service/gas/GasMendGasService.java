package com.osd.sync.service.gas;

import com.osd.sync.entity.gas.GasMendGasEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.osd.sync.entity.gas.GasMeterEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasMendGasService extends IService<GasMendGasEntity> {
    boolean insertMendGas(List<GasMendGasEntity> gasMendGasEntity);
}
