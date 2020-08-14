package com.osd.sync.service.mydb;

import com.osd.sync.entity.mydb.CommunityEntity;
import com.osd.sync.mapper.mydb.CommunityMapper;
import com.osd.sync.service.mydb.CommunityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 小区表 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, CommunityEntity> implements CommunityService {

    @Override
    public Map<Integer, String> getOneCommunity() {
        Map<Integer, String> map = new HashMap<>();
        this.list().forEach(item -> {
            map.put(item.getCommId(), item.getCommName());
        });
        return map;
    }
}
