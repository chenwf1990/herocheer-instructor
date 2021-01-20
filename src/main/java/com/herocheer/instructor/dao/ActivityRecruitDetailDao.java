package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

/**
 * @author makejava
 * @desc  活动招募-明细(ActivityRecruitDetail)表数据库访问层
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
public interface ActivityRecruitDetailDao extends BaseDao<ActivityRecruitDetail,Long>{
    Long deleteDetailByRecruitId(@Param("recruitId") Long recruitId);
}