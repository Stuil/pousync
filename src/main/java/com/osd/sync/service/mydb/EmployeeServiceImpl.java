package com.osd.sync.service.mydb;

import com.osd.sync.entity.mydb.EmployeeEntity;
import com.osd.sync.mapper.mydb.EmployeeMapper;
import com.osd.sync.service.mydb.EmployeeService;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeEntity> implements EmployeeService {

}
