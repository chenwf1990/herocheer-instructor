package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.EquipmentBorrow;
import com.herocheer.instructor.domain.vo.EquipmentBorrowQueryVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  器材借用 (EquipmentBorrow)表数据库访问层
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentBorrowDao extends BaseDao<EquipmentBorrow,Long>{

    /**
     * 查询器材借用列表
     * @param queryVo
     * @return
     */
    List<EquipmentBorrow> findList(EquipmentBorrowQueryVo queryVo);

    /**
     * 回去器材详情
     * @param id
     * @return
     */
    EquipmentBorrowVo getEquipmentBorrow(@Param("id")Long id);

}