package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author chenwf
 * @desc  指导员证书表(InstructorApply)表数据库访问层
 * @date 2021-01-29 08:50:36
 * @company 厦门熙重电子科技有限公司
 */
public interface InstructorApplyDao extends BaseDao<InstructorApply,Long>{

    void batchInsert(List<InstructorApply> instructorApplies);

    /**
     * 根据身份证查找指导员申请
     * @param cardNoList
     * @return
     */
    List<InstructorApply> findByCardNos(List<String> cardNoList);

    /**
     * 指导员申请单列表查询
     * @param instructorQueryVo
     * @return
     */
    List<InstructorApply> queryPageList(InstructorQueryVo instructorQueryVo);

    List<InstructorApply> findByPhones(List<String> phoneList);
}