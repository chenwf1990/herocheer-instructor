package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.EquipmentDamageDetails;
import com.herocheer.instructor.dao.EquipmentDamageDetailsDao;
import com.herocheer.instructor.service.EquipmentDamageDetailsService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author makejava
 * @desc  器材损坏关联借用信息表 (EquipmentDamageDetails)表服务实现类
 * @date 2021-04-25 15:31:31
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class EquipmentDamageDetailsServiceImpl extends BaseServiceImpl<EquipmentDamageDetailsDao, EquipmentDamageDetails,Long> implements EquipmentDamageDetailsService {
    
}