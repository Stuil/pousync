package com.osd.sync.service.gas;

import com.osd.sync.entity.gas.GasUserChargeRecord2019Entity;
import com.osd.sync.entity.gas.GasUserChargeRecord2020Entity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-16
 */
public interface GasUserChargeRecord2020Service extends IService<GasUserChargeRecord2020Entity> {
    boolean insert2020(List<GasUserChargeRecord2020Entity> entityList);
    boolean saveOrUpdates(GasUserChargeRecord2020Entity entityList);
}
