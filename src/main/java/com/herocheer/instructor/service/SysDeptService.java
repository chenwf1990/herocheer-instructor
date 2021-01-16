package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysDept;
import com.herocheer.instructor.domain.vo.SysDeptVO;

import java.util.List;

/**
 * @author gaorh
 * @desc 机构表、部门表、部门架构表(SysDept)表服务接口
 * @date 2021-01-07 17:44:30
 * @company 厦门熙重电子科技有限公司
 */
public interface SysDeptService extends BaseService<SysDept, Long> {
    /**
     * 添加机构
     *
     * @param sysDeptVO VO
     * @return {@link SysDept}
     */
    SysDept addDept(SysDeptVO sysDeptVO);

    /**
     * 删除机构
     *
     * @param id id
     */
    void removeDeptById(Long id);

    /**
     * 发现部门通过id
     *
     * @param id id
     * @return {@link SysDept}
     */
    SysDept findDeptById(Long id);

    /**
     * 修改机构
     *
     * @param sysDeptVO VO
     * @return {@link SysDept}
     */
    SysDept modifyDept(SysDeptVO sysDeptVO);

    /**
     * 机构列表
     *
     * @param sysDeptVO VO
     * @return {@link Page<SysDept>}
     */
    Page<SysDept>  findDeptByPage(SysDeptVO sysDeptVO);

    /**
     * 部门下拉框
     *
     * @return {@link List<SysDept>}
     */
    List<SysDept>  findDept();
}