package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  招募信息主表(ActivityRecruitInfo)表数据库访问层
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
public interface ActivityRecruitInfoDao extends BaseDao<ActivityRecruitInfo,Long>{

    /**
     * 查询招募信息列表
     * @param queryVo
     * @return
     */
    List<ActivityRecruitInfo> findList(ActivityRecruitInfoQueryVo queryVo);

    ActivityRecruitInfoVo getActivityRecruitInfo(@Param("id") Long id);
}