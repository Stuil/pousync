package com.osd.sync.service.gas;

import com.osd.sync.entity.gas.GasRefundGasEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasRefundGasService extends IService<GasRefundGasEntity> {
    boolean insetRefund(List<GasRefundGasEntity> refundGasEntities);
}
