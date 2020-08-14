package com.osd.sync.service.mydb;

import com.osd.sync.entity.mydb.LogEntity;
import com.osd.sync.mapper.mydb.LogMapper;
import com.osd.sync.service.mydb.LogService;
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
public class LogServiceImpl extends ServiceImpl<LogMapper, LogEntity> implements LogService {

}
