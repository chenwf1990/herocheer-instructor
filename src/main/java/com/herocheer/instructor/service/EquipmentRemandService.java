package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.EquipmentRemand;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.EquipmentRemandVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  器材归还记录 (EquipmentRemand)表服务接口
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentRemandService extends BaseService<EquipmentRemand,Long> {
    /**
     * 查询归还信息
     * @param borrowId
     * @param remandStatus
     * @return
     */
    List<EquipmentRemandVo> findRemandList(Long borrowId,Integer remandStatus);

    /**
     * 批量修改状态
     * @param borrowId
     * @param remandStatus
     * @return
     */
    Integer updateRemandStatus(Long borrowId,Integer remandStatus);

    /**
     * 获取器材归还记录
     * @param borrowDetailsId
     * @return
     */
    List<EquipmentRemand> getRemandByDetailsId(Long borrowDetailsId);

    /**
     * 超时未签收的
     * @return
     */
    List<Long> findTimeOutRemand();

}