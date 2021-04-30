package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.aspect.SysMessageEvent;
import com.herocheer.instructor.dao.InstructorApplyAuditLogDao;
import com.herocheer.instructor.dao.InstructorApplyDao;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.instructor.domain.entity.InstructorApplyAuditLog;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.domain.vo.SysMessageVO;
import com.herocheer.instructor.enums.AuditStateEnums;
import com.herocheer.instructor.enums.AuditUnitEnums;
import com.herocheer.instructor.enums.ChannelEnums;
import com.herocheer.instructor.enums.SysMessageEnums;
import com.herocheer.instructor.service.InstructorApplyAuditLogService;
import com.herocheer.instructor.service.InstructorApplyService;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.instructor.service.SysMessageService;
import com.herocheer.instructor.service.SysRoleService;
import com.herocheer.instructor.utils.SpringUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
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
@Slf4j
public class InstructorApplyServiceImpl extends BaseServiceImpl<InstructorApplyDao, InstructorApply,Long> implements InstructorApplyService {
    @Resource
    private InstructorApplyAuditLogDao instructorApplyAuditLogDao;
    @Resource
    private InstructorService instructorService;
    @Resource
    private InstructorApplyAuditLogService instructorApplyAuditLogService;
    @Resource
    private SysRoleService sysRoleService;

    @Autowired
    private SysMessageService sysMessageService;

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
     * @param userEntity
     * @return
     * @author chenwf
     * @desc 指导员申请
     * @date 2021-01-29 08:50:36
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InstructorApply addInstructorApply(InstructorApply instructorApply, UserEntity userEntity) {
        //PC端新增的时候id==指导员id，公众号的id为null，所以没有影响
        instructorApply.setInstructorId(instructorApply.getId());
        if(ChannelEnums.imp.getType() == instructorApply.getChannel()
                || ChannelEnums.pc.getType() == instructorApply.getChannel()){
            instructorApply.setAuditState(AuditStateEnums.to_pass.getState());
            instructorApply.setAuditTime(System.currentTimeMillis());
        }else{
            Map<String,Object> applyMap = new HashMap<>();
            if(StringUtils.isNotEmpty(userEntity.getOtherId())) {
                applyMap.put("openId", userEntity.getOtherId());
            }else{
                applyMap.put("phone", instructorApply.getPhone());
            }
            List<InstructorApply> applyList = this.dao.findByLimit(applyMap);
            if(!applyList.isEmpty()){
                //是否存在待审核数据
                long applyCount = applyList.stream().filter(s ->s.getAuditState() == AuditStateEnums.to_audit.getState()).count();
                if(applyCount > 0){
                    throw new CommonException("您已申请，请等待平台审核");
                }
                applyCount = applyList.stream().filter(s ->s.getAuditState() == AuditStateEnums.to_reject.getState()).count();
                if(applyCount > 0){
                    throw new CommonException("您的申请被驳回，请到个人-我的认证修改重新提交");
                }
            }
            instructorApply.setToken(userEntity.getToken());
            instructorApply.setOpenId(userEntity.getOtherId());
            instructorApply.setAuditState(AuditStateEnums.to_audit.getState());
        }
        if(StringUtils.isEmpty(instructorApply.getAuditUnitName())){
            instructorApply.setAuditUnitName(AuditUnitEnums.getName(instructorApply.getAuditUnitType()));
        }
        //添加指导员数据
        Instructor instructor = instructorService.saveInstructor(instructorApply);
        if(instructor != null) {
            instructorApply.setInstructorId(instructor.getId());
            instructorApply.setUserId(instructor.getUserId());
        }
        //导入的进行批量插入操作,instructorApply.getId() != null意思是编辑的也需要插入数据
        if(ChannelEnums.imp.getType() != instructorApply.getChannel() || instructorApply.getId() != null) {
            this.dao.insert(instructorApply);
        }

        // 采集系统消息
        log.debug("指导员认证系统消息采集");
        SpringUtil.publishEvent(new SysMessageEvent(new SysMessageVO(SysMessageEnums.INSTRUCTOR_AUTH.getText(),SysMessageEnums.INSTRUCTOR_AUTH.getType(),SysMessageEnums.INSTRUCTOR_AUTH.getCode(),instructorApply.getId())));
        return instructorApply;
    }

    private void insertLog(InstructorApply instructorApply) {
        InstructorApplyAuditLog log = new InstructorApplyAuditLog();
        BeanUtils.copyProperties(instructorApply,log);
        log.setApplyId(instructorApply.getId());
        log.setCreatedBy(null);
        log.setCreatedTime(null);
        log.setCreatedId(null);
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

        // 同步系统消息状态
        sysMessageService.modifyMessage(Arrays.asList(SysMessageEnums.INSTRUCTOR_AUTH.getCode()), instructorApply.getId(),false,false);

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
        //添加指导员数据
        Instructor instructor = instructorService.saveInstructor(apply);
        if(instructor != null){
            apply.setInstructorId(instructor.getId());
        }
        this.dao.update(apply);
        //写入日志
        insertLog(apply);

    }

    /**
     * 获取指导员认证信息
     *
     * @param phone
     * @param instructorId
     * @return
     */
    @Override
    public List<InstructorApply> getAuthInfo(String phone, Long instructorId) {
        Map<String,Object> params = new HashMap<>();
        if(StringUtils.isNotEmpty(phone)){
            params.put("phone", phone);
        }else {
            params.put("instructorId", null != instructorId?instructorId:99999L);
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

    /**
     * 找到最新的认证数据
     *
     * @param userId 用户id
     * @return {@link InstructorApply}
     */
    @Override
    public InstructorApply findInstructorApplyByLastes(Long userId) {
        Map<String, Object> objectMap = new HashMap();
        objectMap.put("userId", userId);
        return this.dao.selectInstructorApplyByLastes(objectMap);
    }
}