package com.osd.sync.service.mydb;

import com.osd.sync.entity.mydb.ConsumerEntity;
import com.osd.sync.mapper.mydb.ConsumerMapper;
import com.osd.sync.service.mydb.ConsumerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, ConsumerEntity> implements ConsumerService {

}
