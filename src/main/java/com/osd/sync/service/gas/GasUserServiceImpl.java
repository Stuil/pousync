package com.osd.sync.service.gas;

import com.osd.sync.datasource.DataSource;
import com.osd.sync.datasource.DataSourceEnum;
import com.osd.sync.entity.gas.GasUserEntity;
import com.osd.sync.mapper.gas.GasUserMapper;
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
public class GasUserServiceImpl extends ServiceImpl<GasUserMapper, GasUserEntity> implements GasUserService {


    @Override
    public boolean insetUser(GasUserEntity gasUserEntity) {
        return this.saveOrUpdate(gasUserEntity);
    }
}
