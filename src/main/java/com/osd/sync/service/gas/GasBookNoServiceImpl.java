package com.osd.sync.service.gas;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.osd.sync.datasource.DataSource;
import com.osd.sync.datasource.DataSourceEnum;
import com.osd.sync.entity.gas.GasAreaCommunityEntity;
import com.osd.sync.entity.gas.GasBookNoEntity;
import com.osd.sync.mapper.gas.GasBookNoMapper;
import com.osd.sync.service.gas.GasBookNoService;
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
public class GasBookNoServiceImpl extends ServiceImpl<GasBookNoMapper, GasBookNoEntity> implements GasBookNoService {
    @Override
    public GasBookNoEntity getBooks(String communityId) {
        return this.list(new QueryWrapper<GasBookNoEntity>()
                .eq("communityId",communityId).orderByDesc("id")).get(0);
    }

    @Override
    public GasBookNoEntity getOnes(Wrapper<GasBookNoEntity> queryWrapper) {
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean saves(GasBookNoEntity bookNoEntity) {
        return this.save(bookNoEntity);
    }

    @Override
    public boolean updates(GasBookNoEntity bookNoEntity) {
        return this.updateById(bookNoEntity);
    }
}
