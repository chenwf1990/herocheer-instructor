package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.BrandInfo;
import com.herocheer.instructor.domain.entity.EquipmentInfo;
import com.herocheer.instructor.domain.vo.EquipmentInfoQueryVo;
import com.herocheer.instructor.domain.vo.EquipmentInfoStockVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  器材信息 (EquipmentInfo)表数据库访问层
 * @date 2021-04-19 17:18:25
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentInfoDao extends BaseDao<EquipmentInfo,Long>{
    /**
     * 查询器材列表
     * @param vo
     * @return
     */
    List<EquipmentInfo> findList(EquipmentInfoQueryVo vo);

    /**
     * 库存查询
     * @param vo
     * @return
     */
    List<EquipmentInfoStockVo> findStockList(EquipmentInfoQueryVo vo);
}