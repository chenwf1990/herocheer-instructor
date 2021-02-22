package com.herocheer.instructor.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.InstructorApplyDao;
import com.herocheer.instructor.dao.InstructorDao;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.enums.*;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.instructor.service.SysAreaService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.instructor.utils.ExcelUtil;
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
public class InstructorServiceImpl extends BaseServiceImpl<InstructorDao, Instructor,Long> implements InstructorService {
    @Resource
    private SysAreaService sysAreaService;
    @Resource
    private UserService userService;
    @Resource
    private InstructorApplyDao instructorApplyDao;

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
     * @param multipartFile
     * @param request
     * @return
     * @author chenwf
     * @desc 指导员导入
     * @date 2021-01-14 17:26:18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void instructorImport(MultipartFile multipartFile, HttpServletRequest request) {
        try {
            String originalFilename = multipartFile.getOriginalFilename().toLowerCase();
            if(!originalFilename.contains(".xls") && !originalFilename.contains(".xlsx")){
                throw new CommonException("模板错误，数据导入失败");
            }
            List<Instructor> instructors = new ArrayList<>();
            ExcelReader reader = ExcelUtil.getReader(multipartFile.getInputStream());
            //第一行是标题，第二行是标
            List<List<Object>> read = reader.read(1,1000);
            List<Object> title = read.get(0);
            //获取所有地区数据
            List<SysArea> areas = sysAreaService.findByLimit(new HashMap<>());
            //判断是否已经导入过
            List<String> cardNoList = new ArrayList<>();
            for (int i = 1; i < read.size(); i++) {
                cardNoList.add(read.get(i).get(2).toString());//身份证
            }
            List<InstructorApply> applies = this.instructorApplyDao.findByCardNos(cardNoList);
            if(!applies.isEmpty()){
                String cardNos = applies.stream().map(s ->s.getCardNo()).distinct().collect(Collectors.joining(","));
                throw new CommonException("{}:已存在",cardNos);
            }
            for (int i = 0; i < read.size(); i++) {
                if(i == 0){//标题行
                    continue;
                }
                List<Object> dataList = read.get(i);
                for (int j = 0; j < dataList.size(); j++) {
                    boolean required = ExcelUtil.isRequired(title.get(j), dataList.get(j));
                    if(!required){
                        throw new CommonException("第【"+(i+2)+"】行，第【"+(j+1)+"】列："+title.get(j)+"必填选项");
                    }
                }
                String errMsg = "第【"+(i+2)+"】行，第【"+(i+1)+"】列：";
                Instructor instructor = this.buildInstructor(dataList,errMsg,areas);
                instructors.add(instructor);
            }
            if(!instructors.isEmpty()){
                List<InstructorApply> applyList = new ArrayList<>();
                for (Instructor instructor : instructors) {
                    this.dao.insert(instructor);
                    InstructorApply apply = new InstructorApply();
                    BeanUtils.copyProperties(instructor,apply);
                    apply.setInstructorId(instructor.getId());
                    applyList.add(apply);
                }
                //批量插入申请单
                this.instructorApplyDao.batchInsert(applyList);
            }
        } catch (CommonException e){
            throw new CommonException(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            throw new CommonException("模板错误，数据导入失败");
        }
    }

    private Instructor buildInstructor(List<Object> dataList, String errMsg, List<SysArea> areas) {
        Instructor instructor = new Instructor();
        instructor.setChannel(ChannelEnums.imp.getType());
        instructor.setAuditState(AuditStateEnums.to_pass.getState());
        instructor.setName(dataList.get(0).toString());
        instructor.setSex(SexEnums.getType(dataList.get(1).toString()));
        //是否存在该指导员信息
        instructor.setCardNo(dataList.get(2).toString());
        Map<String,Object> params = new HashMap<>();
        params.put("cardNo",instructor.getCardNo());
        int count = this.dao.count(params);
        if(count > 0){
            throw new CommonException("{}{}已存在该指导员数据",errMsg,instructor.getCardNo());
        }
        instructor.setWorkUnit(dataList.get(3).toString());
        instructor.setChannel(ChannelEnums.imp.getType());
        instructor.setPhone(dataList.get(4).toString());
        instructor.setCertificateNo(dataList.get(5).toString());
        instructor.setCertificateGrade(dataList.get(6).toString());
        instructor.setGuideProject(dataList.get(7).toString());
        instructor.setGuideStation(dataList.get(8).toString());
        Date date = DateUtil.isFormat(dataList.get(9),DateUtil.YYYY_MM_DD_1);
        if(date == null){
            throw new CommonException("{}发证日期格式错误：默认格式{}",errMsg,DateUtil.YYYY_MM_DD_1);
        }
        instructor.setOpeningDate(date.getTime());
        instructor.setAuditUnitName(dataList.get(10).toString());
        instructor.setAuditUnitType(AuditUnitEnums.getType(instructor.getAuditUnitName()));
        if(StringUtils.isEmpty(instructor.getAuditUnitType())){
            throw new CommonException("{}：审批单位错误：{}",errMsg,instructor.getAuditUnitName());
        }
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

        User user = userService.addUser(instructor.getName(), instructor.getCardNo(), instructor.getSex(), instructor.getPhone(), UserTypeEnums.instructor.getCode());
        instructor.setUserId(user.getId());
        instructor.setOpenId(user.getOpenid());
        return instructor;
    }

    private SysArea getAreaCode(List<SysArea> areas, Object areaName, String errMsg, SysArea sysArea) {
        if(ObjectUtil.isNotEmpty(areaName)){
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

    /**
     * @param userId
     * @return
     * @author chenwf
     * @desc 根据用户id查找指导员信息
     * @date 2021-01-21 09:26:18
     */
    @Override
    public Instructor findInstructorByUserId(Long userId) {
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        List<Instructor> instructors = this.dao.findByLimit(param);
        if(!instructors.isEmpty()){
            return instructors.get(0);
        }
        return null;
    }


    /**
     * 添加指导员
     *
     * @param apply
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Instructor saveInstructor(InstructorApply apply) {
        //审核通过才走以下流程
        //PC端的新增和导入，没有审核通过也可以往下走流程
        if(apply.getAuditState() != AuditStateEnums.to_pass.getState()){
            if(ChannelEnums.pc.getType() != apply.getChannel() && ChannelEnums.imp.getType() != apply.getChannel()){
                return null;
            }
        }
        Instructor instructor = new Instructor();
        if(!apply.getChannel().equals(ChannelEnums.h5.getType())) {
            if(apply.getInstructorId() != null){//走编辑流程
                instructor = this.dao.get(apply.getInstructorId());//修改指导员数据
                if(!instructor.getCardNo().equals(apply.getCardNo())){
                    //未修改身份证，不必进行是否存在该身份证的指导员
                    Instructor cardInstructor = this.findByCardNo(apply.getCardNo());
                    if(cardInstructor != null){
                        throw new CommonException("指导员已存在：{}",apply.getCardNo());
                    }
                }
                updateInstructor(instructor,apply);
            }else {
                Instructor cardInstructor = this.findByCardNo(apply.getCardNo());
                if(cardInstructor != null){
                    throw new CommonException("指导员已存在：{}",apply.getCardNo());
                }
                BeanUtils.copyProperties(apply,instructor);
                User user = userService.addUser(apply.getName(), apply.getCardNo(), apply.getSex(), apply.getPhone(), UserTypeEnums.instructor.getCode());
                instructor.setUserId(user.getId());
                instructor.setOpenId(user.getOpenid());
                instructor.setAuditState(AuditStateEnums.to_pass.getState());
                this.dao.insert(instructor);
            }
        }else {
            Instructor cardInstructor = this.findByCardNo(apply.getCardNo());
            if(cardInstructor == null){
                BeanUtils.copyProperties(apply,instructor);
                User user = userService.get(apply.getUserId());
                if(user.getUserType().equals(UserTypeEnums.weChatUser.getCode())){
                    user.setUserType(UserTypeEnums.instructor.getCode());
                    userService.updateUser(user);
                }
                instructor.setUserId(user.getId());
                instructor.setOpenId(user.getOpenid());
                instructor.setAuditState(AuditStateEnums.to_pass.getState());
                this.dao.insert(instructor);
            }else {
                updateInstructor(cardInstructor,apply);
            }

        }
        return instructor;
    }

    private void updateInstructor(Instructor instructor, InstructorApply apply) {
        if(!StringUtils.isEmpty(instructor.getOpenId())) {//存在绑定微信公众号得部分修改
            instructor.setCardNo(null);
            instructor.setPhone(apply.getPhone());
            instructor.setWorkUnit(apply.getWorkUnit());
            instructor.setAreaCode(apply.getAreaCode());
            instructor.setAreaName(apply.getAreaName());
            instructor.setGuideProject(apply.getGuideProject());
            instructor.setCertificateNo(apply.getCertificateNo());
            instructor.setCertificateGrade(apply.getCertificateGrade());
            instructor.setOpeningDate(apply.getOpeningDate());
            instructor.setGuideStation(apply.getGuideStation());
            instructor.setAuditUnitType(apply.getAuditUnitType());
            instructor.setAuditUnitName(apply.getAuditUnitName());
            instructor.setOtherAuditUnitName(apply.getOtherAuditUnitName());
            instructor.setAuditState(AuditStateEnums.to_pass.getState());
            this.dao.update(instructor);
        }else{//否则全部都可以修改
            Instructor update = new Instructor();
            BeanUtils.copyProperties(apply,update);
            update.setId(instructor.getId());
            update.setAuditState(AuditStateEnums.to_pass.getState());
            this.dao.update(update);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteInstructor(Long id) {
        Instructor instructor = this.dao.get(id);
        verifyCanDelOrUpdate(instructor);
        return this.dao.delete(id);
    }

    /**
     * 验证是否可删除或修改
     * @param instructor
     */
    private void verifyCanDelOrUpdate(Instructor instructor) {
        if(instructor.getAuditState() == AuditStateEnums.to_pass.getState()){
            if(!StringUtils.isEmpty(instructor.getOpenId())){
                throw new CommonException("已绑定微信公众号不能删除");
            }
        }
    }

    /**
     * 根据身份证号码获取指导员
     *
     * @param cardNo
     * @return
     */
    @Override
    public Instructor findByCardNo(String cardNo) {
        Map<String,Object> params = new HashMap<>();
        params.put("cardNo",cardNo);
        List<Instructor> instructors = this.dao.findByLimit(params);
        if(!instructors.isEmpty()){
            return instructors.get(0);
        }
        return null;
    }
}