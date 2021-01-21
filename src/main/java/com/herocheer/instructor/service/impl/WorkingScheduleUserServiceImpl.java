package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.WorkingScheduleDao;
import com.herocheer.instructor.dao.WorkingScheduleUserDao;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.vo.WorkingScheduleUserQueryVo;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;
import com.herocheer.instructor.domain.vo.WorkingUserVo;
import com.herocheer.instructor.enums.SignType;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  排班表人员表(WorkingScheduleUser)表服务实现类
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class WorkingScheduleUserServiceImpl extends BaseServiceImpl<WorkingScheduleUserDao, WorkingScheduleUser,Long> implements WorkingScheduleUserService {
    @Resource
    private WorkingScheduleDao workingScheduleDao;

    /**
     * @param workingScheduleUserQueryVo
     * @return
     * @author chenwf
     * @desc 值班人员列表查询
     * @date 2021-01-12 08:57:02
     */
    @Override
    public Page<WorkingSchedulsUserVo> queryPageList(WorkingScheduleUserQueryVo workingScheduleUserQueryVo) {
        Page page = Page.startPage(workingScheduleUserQueryVo.getPageNo(),workingScheduleUserQueryVo.getPageSize());
        List<WorkingSchedulsUserVo> dataList = this.dao.queryPageList(workingScheduleUserQueryVo);
        page.setDataList(dataList);
        return page;
    }

    /**
     * @param workingScheduleUsers
     * @author chenwf
     * @desc 批量插入值班人员信息
     * @date 2021-01-11 14:30:02
     */
    @Override
    public void batchInsert(List<WorkingScheduleUser> workingScheduleUsers) {
        this.dao.batchInsert(workingScheduleUsers);
    }

    /**
     * @param params
     * @author chenwf
     * @desc 根据相关参数删除值班人员信息
     * @date 2021-01-12 08:57:02
     */
    @Override
    public long deleteByMap(Map<String, Object> params) {
        return this.dao.deleteByMap(params);
    }

    /**
     * @param params
     * @return
     * @author chenwf
     * @desc 查找相同时间段的值班人员
     * @date 2021-01-12 08:57:02
     */
    @Override
    public List<String> findWorkingUser(Map<String, Object> params) {
        return this.dao.findWorkingUser(params);
    }

    /**
     * @param workingScheduleUserId
     * @param userId
     * @param replaceCardTime
     * @return
     * @author chenwf
     * @desc 更新打卡时间
     * @date 2021-01-20 20:57:02
     */
    @Override
    public int updateSignTime(Long workingScheduleUserId, Long userId, Long replaceCardTime,Integer type) {
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingScheduleUserId);
        params.put("userId",userId);
        List<WorkingUserVo> workingUserVos = this.workingScheduleDao.getUserWorkingList(params);
        WorkingUserVo workingUserVo = workingUserVos.get(0);
        Long serviceBeginTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceBeginTime());
        Long serviceEndTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceEndTime());
        if(type == SignType.SIGN_IN.getType()){//签到补卡
            //未签到或者签到时间 > 补卡时间，可更新
            if(workingUserVo.getSignInTime() == null || replaceCardTime < workingUserVo.getSignInTime()){
                WorkingScheduleUser scheduleUser = new WorkingScheduleUser();
                scheduleUser.setId(workingUserVo.getWorkingScheduleUserId());
                scheduleUser.setSignInTime(replaceCardTime);
                scheduleUser.setServiceTime((int) (serviceEndTime - replaceCardTime));
                this.dao.update(scheduleUser);
            }
        }else{//签退补卡
            if(workingUserVo.getSignOutTime() == null || replaceCardTime > workingUserVo.getSignOutTime()){
                WorkingScheduleUser scheduleUser = new WorkingScheduleUser();
                scheduleUser.setId(workingUserVo.getWorkingScheduleUserId());
                scheduleUser.setSignOutTime(replaceCardTime);
                scheduleUser.setServiceTime((int) (replaceCardTime - serviceBeginTime));
                this.dao.update(scheduleUser);
            }
        }
        return 1;
    }

    /**
     * 根据打卡时间获取打卡类型
     * @param signTime
     * @param serviceBeginTime
     * @return
     */
    public int getPunchCardType(Long signTime, Long serviceBeginTime) {
        if(signTime < serviceBeginTime + DateUtil.ONE_HOURS) {//签到补卡
            return SignType.SIGN_IN.getType();
        }
        return SignType.SIGN_OUT.getType();
    }
}