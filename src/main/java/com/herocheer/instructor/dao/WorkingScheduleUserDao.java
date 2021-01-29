package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.domain.vo.ServiceHoursReportVo;
import com.herocheer.instructor.domain.vo.ReservationInfoQueryVo;
import com.herocheer.instructor.domain.vo.ReservationInfoVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleUserQueryVo;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  排班表人员表(WorkingScheduleUser)表数据库访问层
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingScheduleUserDao extends BaseDao<WorkingScheduleUser,Long>{
    /**
     * @param workingScheduleUsers
     * @author chenwf
     * @desc 批量插入值班人员信息
     * @date 2021-01-11 14:30:02
     */
    void batchInsert(List<WorkingScheduleUser> workingScheduleUsers);

    /**
     * @param params
     * @author chenwf
     * @desc 根据相关参数删除值班人员信息
     * @date 2021-01-12 08:57:02
     */
    long deleteByMap(Map<String, Object> params);

    /**
     * @author chenwf
     * @desc 值班人员列表查询
     * @date 2021-01-12 14:57:02
     * @param workingScheduleUserQueryVo
     * @return
     */
    List<WorkingSchedulsUserVo> queryPageList(WorkingScheduleUserQueryVo workingScheduleUserQueryVo);

    /**
     * 查找相同时间段的值班人员
     * @param params
     * @return
     */
    List<String> findWorkingUser(Map<String, Object> params);

    /**
     * @param idList
     * @return
     * @author chenwf
     * @desc 根据值班任务id删除值班人员
     * @date 2021-01-21 11:57:02
     */
    int deleteByWorkingScheduleIds(List<Long> idList);

    /**
     * 服务报表统计
     * @param serviceHoursQueryVo
     * @return
     */
    List<ServiceHoursReportVo> serviceHoursReport(ServiceHoursQueryVo serviceHoursQueryVo);

    /**
     * 查询活动预约或服务记录
     * @param queryVo
     * @return
     */
    List<ReservationInfoVo> findReservationInfo(ReservationInfoQueryVo queryVo);
}