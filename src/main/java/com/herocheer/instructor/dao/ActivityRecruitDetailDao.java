package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.vo.ActivityRecruitDetailVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  活动招募-明细(ActivityRecruitDetail)表数据库访问层
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
public interface ActivityRecruitDetailDao extends BaseDao<ActivityRecruitDetail,Long>{
    /**
     * 根据招募信息id删除招募详情
     * @param recruitId
     */
    void deleteDetailByRecruitId(@Param("recruitId") Long recruitId);

    /**
     *
     * @return
     */
    List<ActivityRecruitDetailVo> getRecruitHours(@Param("recruitId")Long recruitId,
                                                  @Param("serviceStartTime") Long serviceStartTime,
                                                  @Param("serviceEndTime") Long serviceEndTime);
}