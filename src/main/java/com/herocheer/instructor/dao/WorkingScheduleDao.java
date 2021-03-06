package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.vo.WorkingScheduleListVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleQueryVo;
import com.herocheer.instructor.domain.vo.WorkingUserVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  排班表(WorkingSchedule)表数据库访问层
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingScheduleDao extends BaseDao<WorkingSchedule,Long>{
    /**
     * @author chenwf
     * @desc  查询排班列表信息
     * @date 2021-01-12 09:42:02
     * @param workingScheduleQueryVo
     * @return
     */
    List<WorkingScheduleListVo> queryPageList(WorkingScheduleQueryVo workingScheduleQueryVo);

    /**
     * @param idList
     * @return
     * @author chenwf
     * @desc 批量删除排班信息
     * @date 2021-01-12 08:47:02
     */
    int batchDelete(List<Long> idList);

    /**
     * @author chenwf
     * @desc 获取值班人员值班信息
     * @date 2021-01-12 08:47:02
     * @param params
     * @return
     */
    List<WorkingUserVo> getUserWorkingList(Map<String, Object> params);

    /**
     * @author chenwf
     * @desc 根据之间id集合获取信息
     * @date 2021-01-21 11:47:02
     * @param idList
     * @return
     */
    List<WorkingSchedule> getByIds(List<Long> idList);

    /**
     * 获取任务信息
     * @param params
     * @return
     */
    List<WorkingUserVo> getTaskInfoList(Map<String, Object> params);

    /**
     * 获取用户的排班信息
     * @param map
     * @return
     */
    WorkingSchedule getUserWorking(Map<String,Object> map);

    /**
     * 可借用日期
     *
     * @param map 地图
     * @return {@link List<WorkingSchedule>}
     */
    List<WorkingSchedule> selectBorrowDate(Map<String,Object> map);

    /**
     * 可借用时段
     *
     * @param map 地图
     * @return {@link List<WorkingSchedule>}
     */
    List<WorkingSchedule> selectBorrowRange(Map<String,Object> map);
}