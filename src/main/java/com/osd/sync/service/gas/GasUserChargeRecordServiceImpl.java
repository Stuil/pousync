package com.osd.sync.service.gas;

import com.osd.sync.datasource.DataSource;
import com.osd.sync.datasource.DataSourceEnum;
import com.osd.sync.entity.gas.GasUserChargeRecordEntity;
import com.osd.sync.mapper.gas.GasUserChargeRecordMapper;
import com.osd.sync.service.gas.GasUserChargeRecordService;
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
public class GasUserChargeRecordServiceImpl extends ServiceImpl<GasUserChargeRecordMapper, GasUserChargeRecordEntity> implements GasUserChargeRecordService {

    @Override
    public boolean insertCharge(List<GasUserChargeRecordEntity> gasUserChargeRecordEntities) {
        return this.saveOrUpdateBatch(gasUserChargeRecordEntities);
    }
}
