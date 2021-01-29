package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.InstructorApplyAuditLogDao;
import com.herocheer.instructor.dao.InstructorApplyDao;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.instructor.domain.entity.InstructorApplyAuditLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.enums.AuditStateEnums;
import com.herocheer.instructor.enums.ChannelEnums;
import com.herocheer.instructor.service.InstructorApplyAuditLogService;
import com.herocheer.instructor.service.InstructorApplyService;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  指导员证书表(InstructorApply)表服务实现类
 * @date 2021-01-29 08:50:36
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class InstructorApplyServiceImpl extends BaseServiceImpl<InstructorApplyDao, InstructorApply,Long> implements InstructorApplyService {
    @Resource
    private InstructorApplyAuditLogDao instructorApplyAuditLogDao;
    @Resource
    private InstructorService instructorService;
    @Resource
    private InstructorApplyAuditLogService instructorApplyAuditLogService;

    /**
     * 指导员申请单列表查询
     *
     * @param instructorQueryVo
     * @return
     */
    @Override
    public Page<InstructorApply> queryPageList(InstructorQueryVo instructorQueryVo) {
        Page page = Page.startPage(instructorQueryVo.getPageNo(),instructorQueryVo.getPageSize());
        List<InstructorApply> applies = this.dao.queryPageList(instructorQueryVo);
        page.setDataList(applies);
        return page;
    }

    /**
     * @param instructorApply
     * @param curUserId
     * @return
     * @author chenwf
     * @desc 指导员申请
     * @date 2021-01-29 08:50:36
     */
    @Override
    public int addInstructorApply(InstructorApply instructorApply, Long curUserId) {
        Map<String,Object> applyMap = new HashMap<>();
        applyMap.put("cardNo",instructorApply.getCardNo());
        applyMap.put("userId",curUserId);
        List<InstructorApply> applyList = this.dao.findByLimit(applyMap);
        if(!applyList.isEmpty()){
            //是否存在待审核数据
            long applyCount = applyList.stream().filter(s ->s.getAuditState() != AuditStateEnums.to_pass.getState()).count();
            if(applyCount > 0){
                throw new CommonException("已申请过，等待审核中");
            }
        }
        if(ChannelEnums.imp.getType() == instructorApply.getChannel()
                || ChannelEnums.pc.getType() == instructorApply.getChannel()){
            instructorApply.setAuditState(AuditStateEnums.to_pass.getState());
        }else{
            instructorApply.setUserId(curUserId);
        }
        int count = this.dao.insert(instructorApply);
        //写入日志
        insertLog(instructorApply);
        //添加指导员数据
        instructorService.saveInstructor(instructorApply);
        return count;
    }


    private void insertLog(InstructorApply instructorApply) {
        InstructorApplyAuditLog log = new InstructorApplyAuditLog();
        BeanUtils.copyProperties(instructorApply,log);
        log.setApplyId(instructorApply.getId());
        instructorApplyAuditLogDao.insert(log);
    }

    /**
     * @param instructorApply
     * @return
     * @author chenwf
     * @desc 编辑指导员申请
     * @date 2021-01-29 08:50:36
     */
    @Override
    public int updateInstructor(InstructorApply instructorApply) {
        InstructorApply model = this.dao.get(instructorApply.getId());
        if(model.getAuditState() == AuditStateEnums.to_pass.getState()){
            throw new CommonException("审核通过不能修改");
        }
        int count = this.dao.update(instructorApply);
        //写入日志
        insertLog(instructorApply);
        return count;
    }

    /**
     * @param id
     * @param auditState
     * @param auditIdea
     * @author chenwf
     * @desc 指导员申请单审批
     * @date 2021-01-29 08:50:36
     */
    @Override
    public void approval(Long id, int auditState, String auditIdea) {
        InstructorApply apply = this.dao.get(id);
        if(apply == null){
            throw new CommonException("指导员不存在");
        }
        if(apply.getAuditState() != AuditStateEnums.to_audit.getState()){
            throw new CommonException("非待审核中");
        }
        apply.setId(id);
        apply.setAuditState(auditState);
        apply.setAuditIdea(auditIdea);
        apply.setAuditTime(System.currentTimeMillis());
        this.dao.update(apply);
        //写入日志
        insertLog(apply);
        //添加指导员数据
        instructorService.saveInstructor(apply);
    }

    /**
     * 获取指导员认证信息
     *
     * @param curUserId
     * @param cardNo
     * @return
     */
    @Override
    public List<InstructorApply> getAuthInfo(Long curUserId, String cardNo) {
        Map<String,Object> params = new HashMap<>();
        if(StringUtils.isEmpty(cardNo)){
            params.put("userId",curUserId);
        }else {
            params.put("cardNo",cardNo);
        }
        params.put("orderBy","id desc");
        List<InstructorApply> applies = this.dao.findByLimit(params);
        return applies;
    }

    /**
     * 获取审批列表信息
     *
     * @param applyId
     * @return
     */
    @Override
    public List<InstructorApplyAuditLog> getApprovalLog(Long applyId) {
        Map<String,Object> params = new HashMap<>();
        params.put("applyId",applyId);
        params.put("orderBy","id desc");

        return instructorApplyAuditLogService.findByLimit(params);
    }
}