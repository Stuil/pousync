package com.osd.sync.service.gas;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.osd.sync.datasource.DataSource;
import com.osd.sync.datasource.DataSourceEnum;
import com.osd.sync.entity.gas.GasAreaCommunityEntity;
import com.osd.sync.mapper.gas.GasAreaCommunityMapper;
import com.osd.sync.service.gas.GasAreaCommunityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
@DataSource(DataSourceEnum.DB2)
public class GasAreaCommunityServiceImpl extends ServiceImpl<GasAreaCommunityMapper, GasAreaCommunityEntity> implements GasAreaCommunityService {

    @Override
    public Map<String, Object> getListAreaToMap() {
        Map<String, Object> map = new HashMap<>();
        this.list().forEach(item -> {
            map.put(item.getId(), item.getName());
        });
        return map;
    }

    @Override
    public GasAreaCommunityEntity getOnes(String name) {
        return this.getOne(new QueryWrapper<GasAreaCommunityEntity>().eq("name",name));
    }

    @Override
    public boolean saves(GasAreaCommunityEntity gasAreaCommunityEntity) {
        return this.save(gasAreaCommunityEntity);
    }

    @Override
    public boolean updates(GasAreaCommunityEntity gasAreaCommunityEntity) {
        return this.update(gasAreaCommunityEntity,new QueryWrapper<GasAreaCommunityEntity>().eq("name",gasAreaCommunityEntity.getName()));
    }
}
