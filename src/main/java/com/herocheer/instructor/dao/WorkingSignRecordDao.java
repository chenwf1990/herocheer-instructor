package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.instructor.domain.vo.MatchSignRecordVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenwf
 * @desc  值班签到记录(WorkingSignRecord)表数据库访问层
 * @date 2021-01-12 11:17:11
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingSignRecordDao extends BaseDao<WorkingSignRecord,Long> {
    /**
     * 查询赛事打卡记录
     * @param activityId
     * @param userName
     * @return
     */
    List<MatchSignRecordVo> findMatchSignRecord(@Param("activityId") Long activityId, @Param("userName") String userName);
}