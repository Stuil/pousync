package com.osd.sync.service.mydb;

import com.osd.sync.entity.mydb.CorporationEntity;
import com.osd.sync.mapper.mydb.CorporationMapper;
import com.osd.sync.service.mydb.CorporationService;
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
public class CorporationServiceImpl extends ServiceImpl<CorporationMapper, CorporationEntity> implements CorporationService {

}
