package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.InstructorApplyAuditLogDao;
import com.herocheer.instructor.dao.InstructorApplyDao;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.instructor.domain.entity.InstructorApplyAuditLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.enums.AuditStateEnums;
import com.herocheer.instructor.enums.ChannelEnums;
import com.herocheer.instructor.service.InstructorApplyAuditLogService;
import com.herocheer.instructor.service.InstructorApplyService;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.instructor.service.SysRoleService;
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
public class InstructorApplyServiceImpl extends BaseServiceImpl<InstructorApplyDao, InstructorApply,Long> implements InstructorApplyService {
    @Resource
    private InstructorApplyAuditLogDao instructorApplyAuditLogDao;
    @Resource
    private InstructorService instructorService;
    @Resource
    private InstructorApplyAuditLogService instructorApplyAuditLogService;
    @Resource
    private SysRoleService sysRoleService;

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
    @Transactional(rollbackFor = Exception.class)
    public int addInstructorApply(InstructorApply instructorApply, Long curUserId) {
        //PC端新增的时候id==指导员id，公众号的id为null，所以没有影响
        instructorApply.setInstructorId(instructorApply.getId());
        Map<String,Object> applyMap = new HashMap<>();
        applyMap.put("cardNo",instructorApply.getCardNo());
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
            instructorApply.setAuditState(AuditStateEnums.to_audit.getState());
        }
        //添加指导员数据
        Instructor instructor = instructorService.saveInstructor(instructorApply);
        if(instructor != null) {
            instructorApply.setInstructorId(instructor.getId());
            instructorApply.setUserId(instructor.getUserId());
        }
        int count = this.dao.insert(instructorApply);
        //写入日志
        insertLog(instructorApply);
        return count;
    }

    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public int updateInstructor(InstructorApply instructorApply) {
        InstructorApply model = this.dao.get(instructorApply.getId());
        if(model.getAuditState() == AuditStateEnums.to_pass.getState()){
            throw new CommonException("审核通过不能修改");
        }
        instructorApply.setAuditState(AuditStateEnums.to_audit.getState());
        int count = this.dao.update(instructorApply);
        //写入日志
        insertLog(instructorApply);
        return count;
    }

    /**
     * @param id
     * @param auditState
     * @param auditIdea
     * @param curUserId
     * @author chenwf
     * @desc 指导员申请单审批
     * @date 2021-01-29 08:50:36
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approval(Long id, int auditState, String auditIdea, Long curUserId) {
        InstructorApply apply = this.dao.get(id);
        if(apply == null){
            throw new CommonException("指导员不存在");
        }
        if(apply.getAuditState() != AuditStateEnums.to_audit.getState()){
            throw new CommonException("非待审核中");
        }
        //检测是否有审批权限
        boolean isAuth = sysRoleService.checkIsAuditAuth(curUserId,apply.getAuditUnitType());
        if(!isAuth){
            throw new CommonException("没有审批的权限");
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
     * @param instructorId
     * @return
     */
    @Override
    public List<InstructorApply> getAuthInfo(Long curUserId, Long instructorId) {
        Map<String,Object> params = new HashMap<>();
        if(instructorId == null){
            params.put("userId",curUserId);
        }else {
            params.put("instructorId", instructorId);
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