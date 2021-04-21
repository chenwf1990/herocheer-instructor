package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.dao.FieldDao;
import com.herocheer.instructor.domain.entity.Field;
import com.herocheer.instructor.service.FieldService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author gaorh
 * @desc 场地设施表(Field)表服务实现类
 * @date 2021-04-21 14:33:52
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class FieldServiceImpl extends BaseServiceImpl<FieldDao, Field, Long> implements FieldService {

}