package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.EquipmentDamageDetails;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.EquipmentDamageDetailsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  器材损坏关联借用信息表 (EquipmentDamageDetails)表服务接口
 * @date 2021-04-25 15:31:31
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentDamageDetailsService extends BaseService<EquipmentDamageDetails,Long> {
    List<EquipmentDamageDetailsVo> findList(Long damageId);
}