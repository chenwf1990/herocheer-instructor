package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.ActivityRecord;
import com.herocheer.instructor.domain.vo.ActivityRecordQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecordVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author makejava
 * @desc  活动记录表(ActivityRecord)表数据库访问层
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
public interface ActivityRecordDao extends BaseDao<ActivityRecord,Long>{

    List<ActivityRecordVo> findList(ActivityRecordQueryVo queryVo);

}