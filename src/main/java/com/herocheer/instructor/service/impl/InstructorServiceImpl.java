package com.herocheer.instructor.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.InstructorDao;
import com.herocheer.instructor.domain.HeaderParam;
import com.herocheer.instructor.domain.entity.*;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.enums.*;
import com.herocheer.instructor.service.*;
import com.herocheer.instructor.utils.ExcelUtils;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private InstructorCertService instructorCertService;
    @Resource
    private SysAreaService sysAreaService;
    @Resource
    private UserService userService;

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
        instructor.setId(id);
        instructor.setAuditState(auditState);
        instructor.setAuditIdea(auditIdea);
        instructor.setAuditTime(System.currentTimeMillis());
        this.dao.update(instructor);
        this.addInstructorCert(instructor);
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
        addInstructorCert(instructor); //添加证书日志
    }

    //添加证书日志
    private void addInstructorCert(Instructor instructor) {
        if(instructor.getAuditState() == InstructorAuditStateEnums.to_pass.getState()){
            InstructorCert cert = new InstructorCert();
            BeanUtils.copyProperties(instructor,cert);
            cert.setInstructorId(instructor.getId());
            this.instructorCertService.insert(cert);
        }
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
            instructor.setAuditState(InstructorAuditStateEnums.to_audit.getState());
        }else{
            instructor.setAuditState(InstructorAuditStateEnums.to_audit.getState());
        }
        long count = this.dao.update(instructor);
        addInstructorCert(instructor);
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

    /**
     * @param instructorId
     * @return
     * @author chenwf
     * @desc 指导员证书修改列表
     * @date 2021-01-14 17:26:18
     */
    @Override
    public List<InstructorCert> getInstructorCertList(Long instructorId) {
        Map<String,Object> param = new HashMap<>();
        param.put("instructorId",instructorId);
        return this.instructorCertService.findByLimit(param);
    }

    /**
     * @param multipartFile
     * @param request
     * @return
     * @author chenwf
     * @desc 指导员导入
     * @date 2021-01-14 17:26:18
     */
    @Override
    public void instructorImport(MultipartFile multipartFile, HttpServletRequest request) {
        try {
            List<Instructor> instructors = new ArrayList<>();
            ExcelReader reader = ExcelUtil.getReader(multipartFile.getInputStream());
            //第一行是标题，第二行是标
            List<List<Object>> read = reader.read(1,1000);
            List<Object> title = read.get(0);
            //获取所有地区数据
            List<SysArea> areas = sysAreaService.findByLimit(new HashMap<>());
            for (int i = 0; i < read.size(); i++) {
                if(i == 0){//标题行
                    continue;
                }
                List<Object> dataList = read.get(i);
                for (int j = 0; j < dataList.size(); j++) {
                    boolean required = ExcelUtils.isRequired(title.get(j), dataList.get(j));
                    if(!required){
                        throw new CommonException("第【"+(i+2)+"】行，第【"+(j+1)+"】列："+title.get(j)+"必填选项");
                    }
                }
                String errMsg = "第【"+(i+2)+"】行，第【"+(i+1)+"】列：";
                Instructor instructor = this.buildInstructor(dataList,errMsg,areas);
                instructors.add(instructor);
            }
            if(!instructors.isEmpty()){
                //批量插入指导员数据
                this.dao.batchInsert(instructors);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Instructor buildInstructor(List<Object> dataList, String errMsg, List<SysArea> areas) {
        Instructor instructor = new Instructor();
        instructor.setChannel(ChannelEnums.imp.getType());
        instructor.setAuditState(InstructorAuditStateEnums.to_pass.getState());
        instructor.setName(dataList.get(0).toString());
        instructor.setSex(SexEnums.getType(dataList.get(1).toString()));
        //是否存在该指导员信息
        instructor.setCardNo(dataList.get(2).toString());
        Map<String,Object> params = new HashMap<>();
        params.put("cardNo",instructor.getCardNo());
        int count = this.dao.count(params);
        if(count > 0){
            throw new CommonException(errMsg+instructor.getCardNo()+"已存在该指导员数据");
        }
        instructor.setWorkUnit(dataList.get(3).toString());
        instructor.setPhone(dataList.get(4).toString());
        instructor.setCertificateNo(dataList.get(5).toString());
        instructor.setCertificateGrade(dataList.get(6).toString());
        instructor.setGuideProject(dataList.get(7).toString());
        instructor.setGuideStation(dataList.get(8).toString());
        instructor.setOpeningDate(((Date) dataList.get(9)).getTime());
        instructor.setAuditUnitName(dataList.get(10).toString());
        instructor.setAuditUnitType(AuditUnitEnums.getType(instructor.getAuditUnitName()));
        instructor.setOtherAuditUnitName(dataList.get(11).toString());
        //12,13,14 常驻区（区）,常驻区（街道）,常驻区（社区）
        String areaCode = "";
        String areaName = "";
        if(dataList.get(12) != null){
            areaName = dataList.get(12).toString();
            SysArea sysArea = getAreaCode(areas,dataList.get(12),errMsg,null);
            areaCode = sysArea == null ? areaCode : sysArea.getAreaCode();
            sysArea = getAreaCode(areas,dataList.get(13),errMsg,sysArea);
            areaCode = sysArea == null ? areaCode : sysArea.getAreaCode();
            areaName = sysArea == null ? areaName : areaName + "/" +sysArea.getAreaName();
            sysArea = getAreaCode(areas,dataList.get(14),errMsg,sysArea);
            areaCode = sysArea == null ? areaCode : sysArea.getAreaCode();
            areaName = sysArea == null ? areaName : areaName + "/" +sysArea.getAreaName();
        }
        instructor.setAreaCode(areaCode);
        instructor.setAreaName(areaName);
        instructor.setCertificatePic(dataList.get(15).toString());

        User user = userService.addUser(instructor.getName(), instructor.getCardNo(), instructor.getSex(), instructor.getPhone(), 2);
        instructor.setUserId(user.getId());
        return instructor;
    }

    private SysArea getAreaCode(List<SysArea> areas, Object areaName, String errMsg, SysArea sysArea) {
        if(areaName != null){
            List<SysArea> list = areas.stream().filter(s -> s.getAreaName().equals(areaName)).collect(Collectors.toList());
            if(sysArea != null){
                list = list.stream().filter(s -> s.getPid().equals(sysArea.getId())).collect(Collectors.toList());
            }
            if(list.isEmpty()){
                throw new CommonException(errMsg+"地区输入错误");
            }
            return list.get(0);
        }
        return sysArea;
    }
}