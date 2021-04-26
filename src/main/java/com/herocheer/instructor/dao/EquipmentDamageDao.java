package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.EquipmentDamage;
import com.herocheer.instructor.domain.vo.EquipmentDamageVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  器材损坏 (EquipmentDamage)表数据库访问层
 * @date 2021-04-25 15:31:31
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentDamageDao extends BaseDao<EquipmentDamage,Long>{

    List<EquipmentDamageVo> findList(@Param("borrowId")Long borrowId);

}