package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.EquipmentRemand;
import com.herocheer.instructor.domain.vo.EquipmentRemandVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author makejava
 * @desc  器材归还记录 (EquipmentRemand)表数据库访问层
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentRemandDao extends BaseDao<EquipmentRemand,Long>{

    /**
     * 查询归还信息
     * @param borrowId
     * @param remandStatus
     * @return
     */
    List<EquipmentRemandVo> findRemandList(@Param("borrowId")Long borrowId, @Param("remandStatus")Integer remandStatus);

    /**
     * 批量修改状态
     * @param borrowId
     * @param remandStatus
     * @return
     */
    Integer updateRemandStatus(@Param("borrowId")Long borrowId,@Param("remandStatus")Integer remandStatus);


    /**
     * 获取器材归还记录
     * @param borrowDetailsId
     * @return
     */
    List<EquipmentRemand> getRemandByDetailsId(@Param("borrowDetailsId")Long borrowDetailsId);

    /**
     * 获取超时未签收
     * @return
     */
    List<Long> findTimeOutRemand();
}