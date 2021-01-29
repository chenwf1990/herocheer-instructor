package com.herocheer.instructor.service.impl;

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
@Transactional
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
    public void instructorImport(MultipartFile multipartFile, HttpServletRequest request) {
        try {
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
                String cardNos = applies.stream().map(s ->s.getCardNo()).collect(Collectors.joining(","));
                throw new CommonException(cardNos + ":已存在");
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
                //批量插入指导员数据
                this.dao.batchInsert(instructors);
                //批量插入申请单
                this.instructorApplyDao.batchInsert(instructors);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            throw new CommonException(errMsg+instructor.getCardNo()+"已存在该指导员数据");
        }
        instructor.setWorkUnit(dataList.get(3).toString());
        instructor.setChannel(ChannelEnums.imp.getType());
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

        User user = userService.addUser(instructor.getName(), instructor.getCardNo(), instructor.getSex(), instructor.getPhone(), UserTypeEnums.instructor.getCode());
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
            instructors.get(0);
        }
        return null;
    }


    /**
     * 添加指导员
     *
     * @param apply
     */
    @Override
    public void saveInstructor(InstructorApply apply) {
        if(apply.getAuditState() == AuditStateEnums.to_pass.getState()){
            if(ChannelEnums.pc.getType() != apply.getChannel() && ChannelEnums.imp.getType() != apply.getChannel()){
                return;
            }
        }
        Instructor instructor = new Instructor();
        Map<String,Object> params = new HashMap<>();
        params.put("cardNo",apply.getCardNo());
        List<Instructor> instructors = this.dao.findByLimit(params);
        if(instructors.isEmpty()){//不存在  插入指导员数据
            BeanUtils.copyProperties(apply,instructor);
            this.dao.insert(instructor);
        }else{
            //更新指导员数据
            instructor = instructors.get(0);
            if(!StringUtils.isEmpty(instructor.getOpenId())) {//存在绑定微信公众号得部分修改
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
                this.dao.update(instructor);
            }else{//否则全部都可以修改
                Instructor update = new Instructor();
                BeanUtils.copyProperties(apply,update);
                update.setId(instructor.getId());
                this.dao.update(update);
            }
        }
    }

    @Override
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
}