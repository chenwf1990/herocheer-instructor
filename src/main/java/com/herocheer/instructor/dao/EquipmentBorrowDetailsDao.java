package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.instructor.domain.vo.EquipmentBorrowDetailsVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  器材借用详情 (EquipmentBorrowDetails)表数据库访问层
 * @date 2021-04-20 15:35:26
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentBorrowDetailsDao extends BaseDao<EquipmentBorrowDetails,Long>{
    /**
     * 获取未归还的器材数量
     * @param equipmentId
     * @return
     */
    Integer getUnreturnedQuantity(@Param("equipmentId")Long equipmentId);

    /**
     * 获取器材借用记录
     * @param borrowId
     * @return
     */
    List<EquipmentBorrowDetailsVo> getDetailsByBorrowId(@Param("borrowId")Long borrowId);
}