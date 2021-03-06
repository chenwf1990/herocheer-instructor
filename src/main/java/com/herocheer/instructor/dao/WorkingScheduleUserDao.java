package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.vo.DutyStatisVO;
import com.herocheer.instructor.domain.vo.MatchStatisVO;
import com.herocheer.instructor.domain.vo.ReservationInfoQueryVo;
import com.herocheer.instructor.domain.vo.ReservationInfoVo;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.domain.vo.ServiceHoursReportVo;
import com.herocheer.instructor.domain.vo.ServiceTotalVO;
import com.herocheer.instructor.domain.vo.WorkingScheduleUserQueryVo;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 修改预约状态
     * @param map
     * @return
     */
    Integer updateReserveStatus(Map<String,Object> map);

    /**
     * 获取招募活动的打卡记录
     * @param activityId
     * @return
     */
    List<String> findSignRecord(@Param("activityId")Long activityId);


    /**
     * 定时处理时长审核数据
     *
     * @param map 地图
     * @return {@link List<WorkingSchedulsUserVo>}
     */
    List<WorkingSchedulsUserVo> selectWorkingUserByCheck(Map<String,Object> map);

    /**
     * 获取当天真的值班人员信息
     * @param map
     * @return
     */
    List<WorkingSchedulsUserVo> findNowadaysWorkingUser(Map<String,Object> map);

    /**
     * 获取值班人员的驿站id
     * @param map
     * @return
     */
    List<Long> findCourierStationId(Map<String,Object> map);

    /**
     * 值班服务时长统计
     *
     * @param dutyStatisVO 责任统计学的签证官
     * @return {@link List<DutyStatisVO>}
     */
    List<DutyStatisVO> selectDutyStatisByPage(DutyStatisVO dutyStatisVO);

    /**
     * 赛事服务时长统计
     *
     * @param matchStatisVO 与统计学的签证官
     * @return {@link List<MatchStatisVO>}
     */
    List<MatchStatisVO> selectMatchStatisByPage(MatchStatisVO matchStatisVO);

    /**
     * 服务时长汇总
     *
     * @param serviceTotalVO 服务总签证官
     * @return {@link List<ServiceTotalVO>}
     */
    List<ServiceTotalVO> selectTotalStatisByPage(ServiceTotalVO serviceTotalVO);
}