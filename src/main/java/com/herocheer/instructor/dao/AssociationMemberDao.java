package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.AssociationMember;
import com.herocheer.instructor.domain.vo.AssociationMemberQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author makejava
 * @desc  协会成员(AssociationMember)表数据库访问层
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
public interface AssociationMemberDao extends BaseDao<AssociationMember,Long>{

    List<AssociationMember> findList(AssociationMemberQueryVo queryVo);

}