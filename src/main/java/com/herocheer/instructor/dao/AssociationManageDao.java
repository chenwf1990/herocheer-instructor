package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.AssociationManage;
import com.herocheer.instructor.domain.vo.AssociationDictVo;
import com.herocheer.instructor.domain.vo.AssociationManageQueryVo;
import com.herocheer.instructor.domain.vo.AssociationManageVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author makejava
 * @desc  协会管理(AssociationManage)表数据库访问层
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
public interface AssociationManageDao extends BaseDao<AssociationManage,Long>{
    /**
     * 协会列表
     * @param vo
     * @return
     */
    List<AssociationManageVo> findList(AssociationManageQueryVo vo);

    /**
     * 获取协会字典
     * @return
     */
    List<AssociationDictVo> getAssociationDict();
}