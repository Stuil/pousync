package com.osd.sync.service.mydb;

import com.osd.sync.entity.mydb.WaterpriceEntity;
import com.osd.sync.mapper.mydb.WaterpriceMapper;
import com.osd.sync.service.mydb.WaterpriceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用气类型表 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
public class WaterpriceServiceImpl extends ServiceImpl<WaterpriceMapper, WaterpriceEntity> implements WaterpriceService {

}
