package com.osd.sync.service.gas;

import com.osd.sync.datasource.DataSource;
import com.osd.sync.datasource.DataSourceEnum;
import com.osd.sync.entity.gas.GasMeterEntity;
import com.osd.sync.mapper.gas.GasMeterMapper;
import com.osd.sync.service.gas.GasMeterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class GasMeterServiceImpl extends ServiceImpl<GasMeterMapper, GasMeterEntity> implements GasMeterService {

    @Override
    public boolean insertMeter(GasMeterEntity gasMeterEntity) {
        return this.save(gasMeterEntity);
    }
}
