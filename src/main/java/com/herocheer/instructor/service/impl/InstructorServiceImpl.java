package com.herocheer.instructor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.InstructorDao;
import com.herocheer.instructor.domain.HeaderParam;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.enums.ClientEnums;
import com.herocheer.instructor.enums.InstructorAuditStateEnums;
import com.herocheer.instructor.service.InstructorLogService;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  指导员表(Instructor)表服务实现类
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class InstructorServiceImpl extends BaseServiceImpl<InstructorDao, Instructor,Long> implements InstructorService {
    @Resource
    private InstructorLogService instructorLogService;
    @Resource
    private RedisClient redisClient;

    /**
     * @param instructorQueryVo
     * @return
     * @author chenwf
     * @desc 指导员列表查询
     * @date 2021-01-04 17:26:18
     */
    @Override
    public Page<Instructor> queryPageList(InstructorQueryVo instructorQueryVo) {
        Page page = Page.startPage(instructorQueryVo.getPageNo(),instructorQueryVo.getPageSize());
        List<Instructor> instructors = this.dao.queryPageList(instructorQueryVo);
        page.setDataList(instructors);
        return page;
    }

    /**
     * @param id
     * @param auditState
     * @param auditIdea
     * @return
     * @author chenwf
     * @desc 指导员审批
     * @date 2021-01-04 17:26:18
     */
    @Override
    public void approval(Long id, int auditState, String auditIdea) {
        Instructor instructor = this.dao.get(id);
        if(instructor == null){
            throw new CommonException("指导员不存在");
        }
        if(instructor.getAuditState() != InstructorAuditStateEnums.to_audit.getState()){
            throw new CommonException("非待审核中");
        }
        Instructor update = new Instructor();
        update.setId(id);
        update.setAuditState(auditState);
        update.setAuditIdea(auditIdea);
        this.dao.update(update);
        //增加审批日志
        instructorLogService.addLog(id,auditState,auditIdea,null);
        if(auditState == InstructorAuditStateEnums.to_pass.getState()){
            //TODO chenwf 更新用户类型
        }
    }

    /**
     * @param instructorId
     * @return
     * @author chenwf
     * @desc 指导员审批日志列表
     * @date 2021-01-04 17:26:18
     */
    @Override
    public List<InstructorLog> getApprovalLog(Long instructorId) {
        Map<String,Object> params = new HashMap<>();
        params.put("instructorId",instructorId);
        return this.instructorLogService.findByLimit(params);
    }

    /**
     * @param instructor
     * @param userId
     * @author chenwf
     * @desc 添加指导员
     * @date 2021-01-04 17:26:18
     */
    @Override
    public void addInstructor(Instructor instructor, Long userId) {
        int channel = HeaderParam.getInstance().getClient();
        instructor.setChannel(channel);
        if(ClientEnums.pc.getType() == channel){
            instructor.setAuditState(InstructorAuditStateEnums.to_pass.getState());
        }else{
            instructor.setUserId(userId);
        }
        this.dao.insert(instructor);
        instructorLogService.addLog(instructor.getId(),instructor.getAuditState(),instructor.getAuditIdea(),"新增");
    }

    /**
     * @param instructor
     * @return
     * @author chenwf
     * @desc 编剧指导员
     * @date 2021-01-04 17:26:18
     */
    @Override
    public long updateInstructor(Instructor instructor) {
        Instructor model = this.dao.get(instructor.getId());
        if(model.getAuditState() == InstructorAuditStateEnums.to_pass.getState()){
            throw new CommonException("审核已通过");
        }
        instructor.setAuditState(InstructorAuditStateEnums.to_audit.getState());
        long count = this.dao.update(instructor);
        instructorLogService.addLog(instructor.getId(),instructor.getAuditState(),instructor.getAuditIdea(),"修改");
        return count;
    }

    @Override
    public void loginTest(String token) {
        JSONObject json = new JSONObject();
        json.put("id",1);
        json.put("userName","chenweifeng");
        json.put("userType",1);
        json.put("phone","13655080001");
        redisClient.set(token,json.toJSONString());
    }
}