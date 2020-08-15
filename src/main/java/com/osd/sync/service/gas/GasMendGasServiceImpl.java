package com.osd.sync.service.gas;

import com.osd.sync.datasource.DataSource;
import com.osd.sync.datasource.DataSourceEnum;
import com.osd.sync.entity.gas.GasMendGasEntity;
import com.osd.sync.entity.gas.GasMeterEntity;
import com.osd.sync.mapper.gas.GasMendGasMapper;
import com.osd.sync.service.gas.GasMendGasService;
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
public class GasMendGasServiceImpl extends ServiceImpl<GasMendGasMapper, GasMendGasEntity> implements GasMendGasService {

    @Override
    public boolean insertMendGas(List<GasMendGasEntity> gasMendGasEntity) {
        return this.saveBatch(gasMendGasEntity);
    }
}
