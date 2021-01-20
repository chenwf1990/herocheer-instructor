package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.ActivityRecord;
import com.herocheer.instructor.dao.ActivityRecordDao;
import com.herocheer.instructor.domain.entity.ActivityReplaceCard;
import com.herocheer.instructor.domain.entity.ActivitySignRecord;
import com.herocheer.instructor.domain.vo.ActivityRecordApprovalVo;
import com.herocheer.instructor.domain.vo.ActivityRecordQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecordVo;
import com.herocheer.instructor.service.ActivityRecordService;
import com.herocheer.instructor.service.ActivityReplaceCardService;
import com.herocheer.instructor.service.ActivitySignRecordService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  活动记录表(ActivityRecord)表服务实现类
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class ActivityRecordServiceImpl extends BaseServiceImpl<ActivityRecordDao, ActivityRecord,Long> implements ActivityRecordService {

    @Resource
    private ActivitySignRecordService activitySignRecordService;

    @Resource
    private ActivityReplaceCardService activityReplaceCardService;

    @Override
    public Page<ActivityRecordVo> queryPage(ActivityRecordQueryVo queryVo) {
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        List<ActivityRecordVo> instructors = dao.findList(queryVo);
        page.setDataList(instructors);
        return page;
    }

    @Override
    public List<ActivitySignRecord> getSignByRecordId(Long recordId) {
        Map<String,Object> map=new HashMap<>();
        map.put("recordId",recordId);
        List<ActivitySignRecord> signRecords=activitySignRecordService.findByLimit(map);
        return signRecords;
    }

    @Override
    public List<ActivityReplaceCard> getReplaceCardByRecordId(Long recordId) {
        Map<String,Object> map=new HashMap<>();
        map.put("recordId",recordId);
        List<ActivityReplaceCard> replaceCards= activityReplaceCardService.findByLimit(map);
        return replaceCards;
    }

    @Override
    public Long approval(ActivityRecordApprovalVo approvalVo, UserEntity userEntity) {
        if(approvalVo.getApprovalType()==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "审批类型不能为空");
        }
        Integer approvalType=approvalVo.getApprovalType();
        ActivityRecord record=new ActivityRecord();
        record.setApprovalType(approvalType);
        record.setActualServiceDuration(approvalVo.getActualServiceDuration());
        record.setApprovalComments(approvalVo.getApprovalComments());
        record.setApprovalId(userEntity.getId());
        record.setApprovalBy(userEntity.getUserName());
        record.setApprovalTime(new Date().getTime()*1000);
        return null;
    }
}