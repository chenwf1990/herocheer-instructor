package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.domain.entity.AssociationManage;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.AssociationDictVo;
import com.herocheer.instructor.domain.vo.AssociationManageQueryVo;
import com.herocheer.instructor.domain.vo.AssociationManageVo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author makejava
 * @desc  协会管理(AssociationManage)表服务接口
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
public interface AssociationManageService extends BaseService<AssociationManage,Long> {

    /**
     * 分页查询协会信息
     * @param queryVo
     * @return
     */
    Page<AssociationManageVo> queryPage(AssociationManageQueryVo queryVo);

    /**
     * 获取协会详情
     * @param id
     * @return
     */
    AssociationManage getAssociation(Long id);

    /**
     * 新增协会
     * @param associationManage
     * @return
     */
    Integer addAssociation(AssociationManage associationManage);

    /**
     * 修改协会信息
     * @param associationManage
     * @return
     */
    Integer updateAssociation(AssociationManage associationManage);

    /**
     * 删除协会
     * @param id
     * @return
     */
    Integer delAssociation(Long id);

    List<AssociationDictVo> getAssociationDict();

}