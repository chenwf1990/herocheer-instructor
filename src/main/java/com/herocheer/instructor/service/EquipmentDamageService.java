package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.EquipmentDamage;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.EquipmentDamageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  器材损坏 (EquipmentDamage)表服务接口
 * @date 2021-04-25 15:31:31
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentDamageService extends BaseService<EquipmentDamage,Long> {
    List<EquipmentDamageVo> findList(Long borrowId);
}