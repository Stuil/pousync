package com.osd.sync.service.gas;

import com.osd.sync.datasource.DataSource;
import com.osd.sync.datasource.DataSourceEnum;
import com.osd.sync.entity.gas.GasRefundGasEntity;
import com.osd.sync.mapper.gas.GasRefundGasMapper;
import com.osd.sync.service.gas.GasRefundGasService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
@DataSource(DataSourceEnum.DB2)
public class GasRefundGasServiceImpl extends ServiceImpl<GasRefundGasMapper, GasRefundGasEntity> implements GasRefundGasService {

    @Override
    public boolean insetRefund(List<GasRefundGasEntity> refundGasEntities) {
        return this.saveOrUpdateBatch(refundGasEntities);
    }

    @Override
    public boolean saveOrUpdates(GasRefundGasEntity refundGasEntities) {
        return this.saveOrUpdate(refundGasEntities);
    }
}
