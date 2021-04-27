package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.EquipmentBorrowDetailsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  器材借用详情 (EquipmentBorrowDetails)表服务接口
 * @date 2021-04-20 15:34:07
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentBorrowDetailsService extends BaseService<EquipmentBorrowDetails,Long> {

    /**
     * 获取未归还的器材数量
     * @param equipmentId
     * @return
     */
    Integer getUnreturnedQuantity(Long equipmentId);

    /**
     * 保存器材详情
     * flag : 0.判断申请借用数据是否超过库存 1.判断实际借用数量是否超过库存
     * @param equipmentBorrowDetails
     * @param flag
     * @return
     */
    Integer saveBorrowDetails(EquipmentBorrowDetails equipmentBorrowDetails,Integer flag);

    /**
     * 获取借用器材信息
     * @param borrowId
     * @return
     */
    List<EquipmentBorrowDetailsVo> getDetailsByBorrowId(Long borrowId);

}