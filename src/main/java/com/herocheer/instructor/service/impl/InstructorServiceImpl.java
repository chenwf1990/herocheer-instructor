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
import com.herocheer.instructor.service.InstructorApplyService;
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
    @Resource
    private InstructorApplyService instructorApplyService;

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
        String originalFilename = multipartFile.getOriginalFilename().toLowerCase();
        if(!originalFilename.contains(".xls") && !originalFilename.contains(".xlsx")){
            throw new CommonException("模板错误，数据导入失败");
        }
        List<InstructorApply> applyList = new ArrayList<>();
        ExcelReader reader;
        try {
            reader = ExcelUtil.getReader(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new CommonException("导入失败");
        }
        //第一行是标题，第二行是标
        List<List<Object>> read = reader.read(1,1000);
        List<Object> title = read.get(0);
        //获取所有地区数据
        List<SysArea> areas = sysAreaService.findByLimit(new HashMap<>());
        List<String> phoneList = new ArrayList<>();
        for (int i = 0; i < read.size(); i++) {
            if(i == 0){//标题行
                continue;
            }
            List<Object> dataList = read.get(i);
            for (int j = 0; j < dataList.size(); j++) {
                boolean required = ExcelUtil.isRequired(title.get(j), dataList.get(j));
                if(!required){
                    throw new CommonException("第【{}】行，第【{}】列：{}必填选项",i+2,i+1,title.get(j));
                }
            }
            phoneList.add(dataList.get(4).toString());//手机号码
        }
        isExistDuplicatedData(phoneList);
        for (int i = 0; i < read.size(); i++) {
            if(i == 0){//标题行
                continue;
            }
            List<Object> dataList = read.get(i);
            String errMsg = "第【"+(i+2)+"】行，第【"+(i+1)+"】列：";
            InstructorApply apply = this.buildInstructor(dataList,errMsg,areas);
            applyList.add(apply);
        }
        if(!applyList.isEmpty()){
            //批量插入申请单
            this.instructorApplyDao.batchInsert(applyList);
        }
    }

    //判断导入是否存在相同的手机号码
    private boolean isExistDuplicatedData(List<String> phoneList) {
        List<String> sameList = phoneList.stream().collect(Collectors.toMap(e -> e, e -> 1, Integer::sum)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet()
                .stream()                              // 所有 entry 对应的 Stream
                .filter(e -> e.getValue() > 1)         // 过滤出元素出现次数大于 1 (重复元素）的 entry
                .map(Map.Entry::getKey)                // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList());// 转化为 List
        if(!sameList.isEmpty()){//存在直接抛出异常
            throw new CommonException(sameList.stream().collect(Collectors.joining(",")) + ":存在重复手机号码");
        }
        return true;
    }

    private InstructorApply buildInstructor(List<Object> dataList, String errMsg, List<SysArea> areas) {
        Instructor instructor = new Instructor();
        instructor.setChannel(ChannelEnums.imp.getType());
        instructor.setAuditState(AuditStateEnums.to_pass.getState());
        instructor.setName(dataList.get(0).toString());
        instructor.setSex(SexEnums.getType(dataList.get(1).toString()));
        //是否存在该指导员信息
        instructor.setCardNo(dataList.get(2).toString());
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
        InstructorApply apply = new InstructorApply();
        BeanUtils.copyProperties(instructor,apply);
        InstructorApply model = instructorApplyService.addInstructorApply(apply, null);
        return model;
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
        return null;
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
     * 根据openid获取指导员
     *
     * @param openid openid
     * @return {@link Instructor}
     */
    @Override
    public Instructor findInstructorByOpenId(String openid) {
        Map<String,Object> param = new HashMap<>();
        param.put("openId",openid);
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
        //根据openId获取用户是否存在社会指导员
        Instructor instructor = null;
        if(StringUtils.isNotEmpty(apply.getOpenId())) {
            instructor = this.findInstructorByOpenId(apply.getOpenId());
        }
        if(instructor != null){//存在社会指导员，走更新用户流程
            //预防只是修改手机号码
            userService.addUser(apply,UserTypeEnums.instructor.getCode(),instructor.getPhone());
            updateInstructor(instructor,apply);
        }else{
            instructor = this.findByPhone(apply.getPhone());
            User user = userService.addUser(apply, UserTypeEnums.instructor.getCode(),apply.getPhone());
            if(instructor == null){
                instructor = new Instructor();
                BeanUtils.copyProperties(apply,instructor);
                instructor.setUserId(user.getId());
                instructor.setOpenId(user.getOpenid());
                instructor.setAuditState(AuditStateEnums.to_pass.getState());
                this.dao.insert(instructor);
            }else{
                updateInstructor(instructor,apply);
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
            instructor.setCertificatePic(apply.getCertificatePic());
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

    @Override
    public Instructor findByPhone(String phone) {
        Map<String,Object> params = new HashMap<>();
        params.put("phone",phone);
        List<Instructor> instructors = this.dao.findByLimit(params);
        if(!instructors.isEmpty()){
            return instructors.get(0);
        }
        return null;
    }
}