package com.osd.sync.service.gas;

import com.osd.sync.datasource.DataSource;
import com.osd.sync.datasource.DataSourceEnum;
import com.osd.sync.entity.gas.GasUserChargeRecord2019Entity;
import com.osd.sync.entity.gas.GasUserChargeRecord2020Entity;
import com.osd.sync.mapper.gas.GasUserChargeRecord2020Mapper;
import com.osd.sync.service.gas.GasUserChargeRecord2020Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-16
 */
@Service
@DataSource(DataSourceEnum.DB2)
public class GasUserChargeRecord2020ServiceImpl extends ServiceImpl<GasUserChargeRecord2020Mapper, GasUserChargeRecord2020Entity> implements GasUserChargeRecord2020Service {

    @Override
    public boolean insert2020(List<GasUserChargeRecord2020Entity> entityList) {
        return this.saveOrUpdateBatch(entityList);
    }
}
