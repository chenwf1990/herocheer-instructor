package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.EquipmentInfo;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.EquipmentInfoQueryVo;
import com.herocheer.instructor.domain.vo.EquipmentInfoStockVo;

/**
 * @author makejava
 * @desc  器材信息 (EquipmentInfo)表服务接口
 * @date 2021-04-19 17:18:25
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentInfoService extends BaseService<EquipmentInfo,Long> {

    Page<EquipmentInfo> queryPage(EquipmentInfoQueryVo queryVo);

    Page<EquipmentInfoStockVo> queryStockPage(EquipmentInfoQueryVo queryVo);

    EquipmentInfo getEquipment(Long id);

    Integer addEquipment(EquipmentInfo equipmentInfo);

    Integer updateEquipment(EquipmentInfo equipmentInfo,Long damageId);

    Integer deleteEquipment(Long id);
}