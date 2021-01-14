package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.WorkingSignRecordDao;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.service.WorkingSignRecordService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  值班签到记录(WorkingSignRecord)表服务实现类
 * @date 2021-01-12 11:17:13
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class WorkingSignRecordServiceImpl extends BaseServiceImpl<WorkingSignRecordDao, WorkingSignRecord,Long> implements WorkingSignRecordService {
    @Resource
    private WorkingScheduleUserService workingScheduleUserService;
    /**
     * @param workingScheduleUserId
     * @return
     * @author chenwf
     * @desc 根据值班人员id获取打卡信息列表
     * @date 2021-01-12 11:17:12
     */
    @Override
    public List<WorkingSignRecord> getPunchCardList(Long workingScheduleUserId) {
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingScheduleUserId);
        List<WorkingSignRecord> signRecords = this.dao.findByLimit(params);
        return signRecords;
    }

    /**
     * @param workingSignRecord
     * @param userEntity
     * @return
     * @author chenwf
     * @desc 添加打卡信息
     * @date 2021-01-12 11:17:12
     */
    @Override
    public long addWorkingSignRecord(WorkingSignRecord workingSignRecord, UserEntity userEntity) {
        WorkingScheduleUser workingScheduleUser = workingScheduleUserService.get(workingSignRecord.getWorkingScheduleUserId());
        if(workingScheduleUser == null){
            throw new CommonException("没有该值班人员");
        }
        //查找今天是否已打卡
        Map<String,Object> params = new HashMap<>();
        params.put("type",workingSignRecord.getType());
        params.put("createdId",userEntity.getId());
        long count = this.dao.count(params);
        if(count > 0){
            throw new CommonException("请勿重复打卡");
        }

        return this.dao.insert(workingSignRecord);
    }
}