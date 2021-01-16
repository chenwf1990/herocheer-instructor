package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysDept;
import com.herocheer.instructor.domain.entity.SysRole;
import com.herocheer.instructor.domain.vo.SysDeptVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author gaorh
 * @desc 机构表、部门表、部门架构表(SysDept)表数据库访问层
 * @date 2021-01-07 17:44:30
 * @company 厦门熙重电子科技有限公司
 */
public interface SysDeptDao extends BaseDao<SysDept, Long> {

    /**
     * 机构列表
     *
     * @param sysDeptVO VO
     * @return {@link List<SysRole>}
     */
    List<SysDept> selectDeptByPage(SysDeptVO sysDeptVO);
}