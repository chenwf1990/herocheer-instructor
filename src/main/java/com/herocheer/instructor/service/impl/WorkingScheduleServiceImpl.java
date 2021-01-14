package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.WorkingScheduleDao;
import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.vo.WorkingScheduleListVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleQueryVo;
import com.herocheer.instructor.domain.vo.WorkingVo;
import com.herocheer.instructor.enums.ScheduleUserAuditStateEnums;
import com.herocheer.instructor.enums.ScheduleUserTypeEnums;
import com.herocheer.instructor.service.WorkingScheduleService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenwf
 * @desc  排班表(WorkingSchedule)表服务实现类
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class WorkingScheduleServiceImpl extends BaseServiceImpl<WorkingScheduleDao, WorkingSchedule,Long> implements WorkingScheduleService {
    @Resource
    private WorkingScheduleUserService workingScheduleUserService;

    /**
     * @param workingScheduleQueryVo
     * @return
     * @author chenwf
     * @desc 排班列表
     * @date 2021-01-12 08:47:02
     */
    @Override
    public Page<WorkingScheduleListVo> queryPageList(WorkingScheduleQueryVo workingScheduleQueryVo) {
        Page page = Page.startPage(workingScheduleQueryVo.getPageNo(),workingScheduleQueryVo.getPageSize());
        List<WorkingScheduleListVo> dataList = this.dao.queryPageList(workingScheduleQueryVo);
        page.setDataList(dataList);
        return page;
    }

    /**
     * @param workingSchedulsVo
     * @author chenwf
     * @desc 添加排班信息
     * @date 2021-01-11 14:30:02
     */
    @Override
    public void addWorkingScheduls(List<WorkingVo> workingSchedulsVo) {
        for (WorkingVo workingVo : workingSchedulsVo) {
            this.dao.insert(workingVo);
            List<WorkingScheduleUser> workingScheduleUsers = workingVo.getWorkingScheduleUsers();
            //设置id
            workingScheduleUsers.forEach(w -> {
                w.setWorkingScheduleId(workingVo.getId());
                if(w.getType() == ScheduleUserTypeEnums.SUBSCRIBEDUTY.getType()){
                    w.setAuditState(ScheduleUserAuditStateEnums.to_pass.getState());
                }
            });
            //批量插入值班人员信息
            workingScheduleUserService.batchInsert(workingScheduleUsers);
        }
    }

    /**
     * @param id
     * @author chenwf
     * @desc 根据id获取排班信息
     * @date 2021-01-11 14:30:02
     */
    @Override
    public WorkingVo getWorkingScheduls(Long id) {
        WorkingVo workingVo = new WorkingVo();
        WorkingSchedule schedule = this.dao.get(id);
        BeanUtils.copyProperties(schedule,workingVo);
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleId",id);
        List<WorkingScheduleUser> scheduleUsers = workingScheduleUserService.findByLimit(params);
        workingVo.setWorkingScheduleUsers(scheduleUsers);
        return workingVo;
    }

    /**
     * @param workingVo
     * @author chenwf
     * @desc 编辑排班信息
     * @date 2021-01-12 08:47:02
     */
    @Override
    public void updateWorkingScheduls(WorkingVo workingVo) {
        if(workingVo.getScheduleTime() < System.currentTimeMillis()){
            throw new CommonException("值班日期中不能修改");
        }
        WorkingSchedule schedule = new WorkingSchedule();
        BeanUtils.copyProperties(workingVo,schedule);
        this.dao.update(schedule);
        List<WorkingScheduleUser> workingScheduleUsers = workingVo.getWorkingScheduleUsers();
        //删除被修改的人员，微信预约人员不能删除
        List<Long> scheduleUserIdList = workingScheduleUsers.stream().
                filter(y -> y.getId() != null).
                map(y -> y.getId()).
                collect(Collectors.toList());
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleId",workingVo.getId());
        params.put("typeList", Arrays.asList(1,2));
        params.put("notDelIdList", scheduleUserIdList);
        workingScheduleUserService.deleteByMap(params);
        //更新或修改值班人员信息
        for (WorkingScheduleUser workingScheduleUser : workingScheduleUsers) {
            if(workingScheduleUser.getId() == null){
                workingScheduleUserService.insert(workingScheduleUser);
            }else {
                workingScheduleUserService.update(workingScheduleUser);
            }
        }
    }

    /**
     * @param ids
     * @return
     * @author chenwf
     * @desc 批量删除排班信息
     * @date 2021-01-12 08:47:02
     */
    @Override
    public long batchDelete(String ids) {
        List<Long> idList = Stream.of(ids.split(",")).map(s -> Long.parseLong(s)).collect(Collectors.toList());
        return this.dao.batchDelete(idList);
    }
}