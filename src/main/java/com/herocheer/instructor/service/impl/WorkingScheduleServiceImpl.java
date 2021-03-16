
package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.WorkingScheduleDao;
import com.herocheer.instructor.domain.entity.*;
import com.herocheer.instructor.domain.vo.ActivityReservationVo;
import com.herocheer.instructor.domain.vo.UserGuideProjectVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleListVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleQueryVo;
import com.herocheer.instructor.domain.vo.WorkingUserInfoVo;
import com.herocheer.instructor.domain.vo.WorkingUserVo;
import com.herocheer.instructor.domain.vo.WorkingVo;
import com.herocheer.instructor.enums.AuditStatusEnums;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.enums.ReserveStatusEnums;
import com.herocheer.instructor.enums.ScheduleUserTypeEnums;
import com.herocheer.instructor.enums.SignStatusEnums;
import com.herocheer.instructor.service.*;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.instructor.utils.ExcelUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenwf
 * @desc  排班表(WorkingSchedule)表服务实现类
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class WorkingScheduleServiceImpl extends BaseServiceImpl<WorkingScheduleDao, WorkingSchedule,Long> implements WorkingScheduleService {
    @Resource
    private WorkingScheduleUserService workingScheduleUserService;
    @Resource
    private CourierStationService courierStationService;
    @Resource
    private ServiceHoursService serviceHoursService;
    @Resource
    private UserService userService;
    @Resource
    private CommonService commonService;
    @Resource
    private WorkingSignRecordService workingSignRecordService;
    @Resource
    private InstructorService instructorService;
    @Resource
    private ActivityRecruitInfoService activityRecruitInfoService;
    @Resource
    private ActivityRecruitDetailService activityRecruitDetailService;
    @Resource
    private WorkingReplaceCardService workingReplaceCardService;
    @Resource
    private ReservationService reservationService;

    /**
     * @param workingScheduleQueryVo
     * @return
     * @author chenwf
     * @desc 排班列表
     * @date 2021-01-12 08:47:02
     */
    @Override
    public Page<WorkingScheduleListVo> queryPageList(WorkingScheduleQueryVo workingScheduleQueryVo) {
        Page page = Page.startPage(workingScheduleQueryVo.getPageNo(),workingScheduleQueryVo.getPageSize());
        List<WorkingScheduleListVo> dataList = this.dao.queryPageList(workingScheduleQueryVo);
        page.setDataList(dataList);
        return page;
    }

    /**
     * @param workingScheduls
     * @author chenwf
     * @desc 添加排班信息
     * @date 2021-01-11 14:30:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addWorkingScheduls(List<WorkingVo> workingScheduls) {
        if(workingScheduls.isEmpty()){
            return;
        }
        //判断是否存在同个时段相同人员
        isSameUser(workingScheduls);
        //查找用户信息
        List<Long> userIdList = new ArrayList<>();
        workingScheduls.forEach(w ->{
            userIdList.addAll(w.getWorkingScheduleUsers().stream().map(s -> s.getUserId()).collect(Collectors.toList()));
        });
        List<UserGuideProjectVo> users = userService.findUserProjectByUserIds(userIdList);
        //查找到的用户信息按用户名称进行聚合，后续根据用户名key查找用户信息velue
        Map<Long, List<UserGuideProjectVo>> userMap = users.stream().collect(Collectors.groupingBy(UserGuideProjectVo::getId));
        for (WorkingVo workingVo : workingScheduls) {
            workingVo.setActivityType(RecruitTypeEunms.STATION_RECRUIT.getType());
            this.dao.insert(workingVo);
            List<WorkingScheduleUser> workingScheduleUsers = workingVo.getWorkingScheduleUsers();
            //设置id
            workingScheduleUsers.forEach(scheduleUser -> {
                UserGuideProjectVo user = userMap.get(scheduleUser.getUserId()).get(0);
                if(scheduleUser.getType() == ScheduleUserTypeEnums.SUBSCRIBE_DUTY.getType()){
                    scheduleUser.setStatus(AuditStatusEnums.to_audit.getState());
                }
                scheduleUser.setTaskNo(DateUtil.getNewTime());
                scheduleUser.setReserveStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());
                scheduleUser.setStatus(AuditStatusEnums.to_audit.getState());//初始化待审核
                scheduleUser.setWorkingScheduleId(workingVo.getId());
                scheduleUser.setGuideProject(StringUtils.isEmpty(user.getInstructorGuideProject()) ? user.getGuideProject() : user.getInstructorGuideProject());
                scheduleUser.setCertificateGrade(user.getCertificateGrade());
            });
            isSameTimeWorkingUser(workingVo,workingScheduleUsers);
            //批量插入值班人员信息
            workingScheduleUserService.batchInsert(workingScheduleUsers);
        }
    }
    //判断前端传送的值班人员是否存在相同时间段的值班人员
    private Boolean isSameUser(List<WorkingVo> workingScheduls) {
        List<String> userList = new ArrayList<>();
        workingScheduls.forEach(v ->{
            String append = DateUtil.format(DateUtil.date(v.getScheduleTime()),DateUtil.YYYY_MM_DD) + "|" + v.getServiceBeginTime() + "~" + v.getServiceEndTime() + "|";
            v.getWorkingScheduleUsers().forEach(u ->{
                String user = append + u.getUserName() + "|" + u.getId();
                userList.add(user);
            });
        });
        isExistDuplicatedData(userList);
        return true;
    }

    //判断同一时间段(排班日期 | 时段 | 排班人员)是否存在相同
    private boolean isExistDuplicatedData(List<String> userList) {
        List<String> sameList = userList.stream().collect(Collectors.toMap(e -> e, e -> 1, Integer::sum)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet()
                .stream()                              // 所有 entry 对应的 Stream
                .filter(e -> e.getValue() > 1)         // 过滤出元素出现次数大于 1 (重复元素）的 entry
                .map(Map.Entry::getKey)                // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList());// 转化为 List
        if(!sameList.isEmpty()){//存在直接抛出异常
            String userName = sameList.stream().collect(Collectors.joining("\n"));
            throw new CommonException(userName.replace("|null","") + "\n存在重复班次");
        }
        return true;
    }

    /**
     * @param id
     * @author chenwf
     * @desc 根据id获取排班信息
     * @date 2021-01-11 14:30:02
     */
    @Override
    public WorkingVo getWorkingScheduls(Long id) {
        WorkingVo workingVo = new WorkingVo();
        WorkingSchedule schedule = this.dao.get(id);
        BeanUtils.copyProperties(schedule,workingVo);
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleId",id);
        List<WorkingScheduleUser> scheduleUsers = workingScheduleUserService.findByLimit(params);
        workingVo.setWorkingScheduleUsers(scheduleUsers);
        return workingVo;
    }

    /**
     * @param workingVo
     * @author chenwf
     * @desc 编辑排班信息
     * @date 2021-01-12 08:47:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWorkingScheduls(WorkingVo workingVo) {
        if(workingVo.getScheduleTime() < System.currentTimeMillis()){
            throw new CommonException("值班日期中不能修改");
        }
        List<WorkingVo> workingVos = new ArrayList<>();
        workingVos.add(workingVo);
        isSameUser(workingVos);
        WorkingSchedule schedule = new WorkingSchedule();
        BeanUtils.copyProperties(workingVo,schedule);
        this.dao.update(schedule);
        List<WorkingScheduleUser> workingScheduleUsers = workingVo.getWorkingScheduleUsers();
        isSameTimeWorkingUser(workingVo,workingScheduleUsers);
        //删除被修改的人员，微信预约人员不能删除
        List<Long> scheduleUserIdList = workingScheduleUsers.stream().
                filter(y -> y.getId() != null).
                map(y -> y.getId()).
                collect(Collectors.toList());
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleId",workingVo.getId());
        params.put("typeList", Arrays.asList(1,2));
        params.put("notDelIdList", scheduleUserIdList);
        workingScheduleUserService.deleteByMap(params);
        //找着用户指导项目数据
        Map<Long, List<UserGuideProjectVo>> userMap = new HashMap<>();
        List<Long> userIdList = workingScheduleUsers.stream().
                filter(y -> y.getId() == null).
                map(y -> y.getUserId()).
                collect(Collectors.toList());
        if(!userIdList.isEmpty()){
            List<UserGuideProjectVo> users = userService.findUserProjectByUserIds(userIdList);
            //查找到的用户信息按用户名称进行聚合，后续根据用户名key查找用户信息velue
            userMap = users.stream().collect(Collectors.groupingBy(UserGuideProjectVo::getId));
        }
        for (WorkingScheduleUser scheduleUser : workingScheduleUsers) {
            scheduleUser.setWorkingScheduleId(workingVo.getId());
            if(scheduleUser.getId() == null){
                UserGuideProjectVo user = userMap.get(scheduleUser.getUserId()).get(0);
                scheduleUser.setGuideProject(StringUtils.isEmpty(user.getInstructorGuideProject()) ? user.getGuideProject() : user.getInstructorGuideProject());
                scheduleUser.setCertificateGrade(user.getCertificateGrade());
                scheduleUser.setTaskNo(DateUtil.getNewTime());
                workingScheduleUserService.insert(scheduleUser);
            }else{
                workingScheduleUserService.update(scheduleUser);
            }
        }
    }

    //判断传送的值班人员是否存在相同时间段的值班人员（数据库查询）
    private void isSameTimeWorkingUser(WorkingSchedule workingVo, List<WorkingScheduleUser> workingScheduleUsers) {
        //判断值班人员是否存在同个时间段的值班
        List<Long> userIdList = workingScheduleUsers.stream().filter(y ->y.getId() == null).map(s -> s.getUserId()).collect(Collectors.toList());
        if(userIdList.isEmpty()){
            return;
        }
        List<String> userNameList = findWorkingUser(userIdList,workingVo);
        if(!userNameList.isEmpty()){
            throw new CommonException(userNameList.stream().collect(Collectors.joining(",")) +"已参加该时段值班");
        }
    }

    /**
     * @param ids
     * @return
     * @author chenwf
     * @desc 批量删除排班信息
     * @date 2021-01-12 08:47:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int  batchDelete(String ids) {
        List<Long> idList = Stream.of(ids.split(",")).map(s -> Long.parseLong(s)).collect(Collectors.toList());
        if(idList.isEmpty()){
            throw new CommonException("请选择要删除的排班信息");
        }
        List<WorkingSchedule> workingSchedules = this.dao.getByIds(idList);
        for (WorkingSchedule schedule : workingSchedules) {
            if(schedule.getScheduleTime() < System.currentTimeMillis()){
                throw new CommonException("值班日期中不能删除:"+DateUtil.format(new Date(schedule.getScheduleTime()),DateUtil.YYYY_MM_DD));
            }
        }
        int count = this.dao.batchDelete(idList);
        //批量删除scheduleUser数据
        workingScheduleUserService.deleteByWorkingScheduleIds(idList);
        return count;
    }

    /**
     * @param courierStationId
     * @param serviceTimeId
     * @param response
     * @author chenwf
     * @desc 排班模板导出
     * @date 2021-01-12 08:47:02
     */
    @Override
    public void templateExport(Long courierStationId, Long serviceTimeId, HttpServletResponse response) {
        //查找驿站
        CourierStation courierStation = this.courierStationService.get(courierStationId);
        if(courierStation == null){
            throw new CommonException("驿站不存在");
        }
        ServiceHours serviceHours = serviceHoursService.get(serviceTimeId);
        if(serviceHours == null){
            throw new CommonException("服务时段不存在");
        }
        String serviceTimes = serviceHours.getServiceTimes();
        if(StringUtils.isEmpty(serviceTimes)){
            throw new CommonException("未配置服务时间段");
        }
        JSONArray jsonArray = JSON.parseArray(serviceTimes);
        String times = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            if(json.isEmpty()){
                continue;
            }
            times = json.getString("startTime") + "~" + json.getString("endTime") + ",";
        }
        //准备将数据集合封装成Excel对象
        List<String> row1 = CollUtil.newArrayList("*所属驿站", "*排班日期", "*时段", "*值班站长","*固定值班人员（人员姓名请用逗号隔开）");
        List<String> row2 = CollUtil.newArrayList(courierStation.getName(), "2020-01-01", "", "张三","李四，王五");
        List<List<String>> rows = CollUtil.newArrayList(row1,row2);
        ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter(true);
        //合并标题单元格
        writer.merge(row1.size() - 1, "值班排班导入模板");
        //设置列宽
        writer.setColumnWidth(0, 20); //第1列20px宽
        writer.setColumnWidth(1, 20); //第2列20px宽
        writer.setColumnWidth(2, 20); //第3列20px宽
        writer.setColumnWidth(3, 20); //第4列20px宽
        writer.setColumnWidth(4, 40); //第5列40px宽
        //设置服务时段下拉选项
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList();
        cellRangeAddressList.addCellRangeAddress(1,2,999,2);
        writer.addSelect(cellRangeAddressList,times);
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        try {
            // 获取输出流
            final OutputStream output = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("值班排班导入模板.xlsx", "UTF-8"));
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            writer.flush(output, true);
            writer.close();
            IoUtil.close(output);
        } catch (IOException e) {
            throw new CommonException("导出Excel异常");
        }
    }

    /**
     * @param courierStationId
     * @param serviceTimeId
     * @param multipartFile
     * @author chenwf
     * @desc 排班信息导入
     * @date 2021-01-19 09:47:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void workingScheduleImport(Long courierStationId, Long serviceTimeId, MultipartFile multipartFile) {
        //查找驿站
        CourierStation courierStation = this.courierStationService.get(courierStationId);
        if(courierStation == null){
            throw new CommonException("驿站不存在");
        }
        ServiceHours serviceHours = serviceHoursService.get(serviceTimeId);
        if(serviceHours == null){
            throw new CommonException("服务时段不存在");
        }
        try {
            ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(multipartFile.getInputStream());
            //第一行是标题，第二行是标
            List<List<Object>> read = reader.read(1,1000);
            List<Object> title = read.get(0);
            //获取excel所有排班人员信息
            String userNames = "";
            List<String> list = new ArrayList<>();
            for (int i = 1; i < read.size(); i++) {//0是标题，数据行从1开始
                List<Object> data = read.get(i);
                for (int j = 0; j < data.size(); j++) {
                    boolean required = ExcelUtil.isRequired(title.get(j), data.get(j));
                    if(!required){
                        throw new CommonException("第【"+(i+2)+"】行，第【"+(j+1)+"】列："+title.get(j)+"必填选项");
                    }
                }
                //以逗号隔开拼接固定人员和值班人员
                userNames += data.get(3).toString() + "," + data.get(4).toString() + ",";
                //排班日期 | 时段 | 排班人员  进行拼接
                String append = data.get(1).toString().trim() + "|" + data.get(2).toString().trim() + "|";
                list.add(append + data.get(3).toString().trim());
                for (String userName : data.get(4).toString().replace("，", ",").replace(" ", "").split(",")) {
                    list.add(append + userName.trim());
                }
            }
            //判断同一时间段(排班日期 | 时段 | 排班人员)是否存在相同
            isExistDuplicatedData(list);
            userNames = userNames.replace("，",",").replace(" ","");//把所有的中文逗号替换成英文逗号
            List<String> userNameList = Arrays.stream(userNames.split(",")).collect(Collectors.toList());
            List<UserGuideProjectVo> users = userService.findUserProjectByUserNames(userNameList);
            //查找到的用户信息按用户名称进行聚合，后续根据用户名key查找用户信息velue
            Map<String, List<UserGuideProjectVo>> userMap = users.stream().collect(Collectors.groupingBy(UserGuideProjectVo::getUserName));
            for (int i = 1; i < read.size(); i++) {//0是标题，数据行从1开始
                List<Object> dataList = read.get(i);
                buildSaveWorkingSchedule(courierStation,serviceHours,dataList,userMap,i);
            }
        } catch (CommonException e){
            throw new CommonException(e.getMessage());
        } catch (Exception e){
            throw new CommonException("模板错误，数据导入失败");
        }

    }

    //组装、保存值班记录
    private void buildSaveWorkingSchedule(CourierStation courierStation, ServiceHours serviceHours, List<Object> dataList, Map<String, List<UserGuideProjectVo>> userMap, int i) {
        WorkingSchedule workingSchedule = new WorkingSchedule();
        workingSchedule.setActivityType(RecruitTypeEunms.STATION_RECRUIT.getType());
        workingSchedule.setCourierStationId(courierStation.getId());
        workingSchedule.setCourierStationName(courierStation.getName());
        workingSchedule.setScheduleTime(DateUtil.parse(dataList.get(1).toString(), DateUtil.YYYY_MM_DD).getTime());
        workingSchedule.setServiceTimeId(serviceHours.getId());
        String[] times = dataList.get(2).toString().split("~");
        if(!serviceHours.getServiceTimes().contains(times[0]) || !serviceHours.getServiceTimes().contains(times[1])){
            throw new CommonException("第【"+(i+2)+"】行，第【"+(2+1)+"】列：" + courierStation.getName() + "不存在该服务时间段");
        }
        workingSchedule.setServiceBeginTime(times[0]);
        workingSchedule.setServiceEndTime(times[1]);
        this.dao.insert(workingSchedule);
        List<WorkingScheduleUser> workingScheduleUserList = new ArrayList<>();
        //值班站长
        String stationUser = dataList.get(3).toString();
        WorkingScheduleUser scheduleUser = buildWorkingScheduleUser(userMap,workingSchedule,stationUser,ScheduleUserTypeEnums.STATION_DUTY.getType());
        workingScheduleUserList.add(scheduleUser);
        //固定值班人员
        String[] fixationUsers = dataList.get(4).toString().replace("，",",").split(",");//把所有的中文逗号替换成英文逗号
        for (String fixationUser : fixationUsers) {
            scheduleUser = buildWorkingScheduleUser(userMap,workingSchedule,fixationUser,ScheduleUserTypeEnums.FIXATION_DUTY.getType());
            workingScheduleUserList.add(scheduleUser);
        }
        //查找是否存在相同值班时段的值班人员
        isSameTimeWorkingUser(workingSchedule,workingScheduleUserList);
        //批量插入值班人员
        workingScheduleUserService.batchInsert(workingScheduleUserList);
    }

    //查找是否存在相同值班时段的值班人员
    private List<String> findWorkingUser(List<Long> userIdList, WorkingSchedule workingSchedule) {
        Map<String,Object> params = new HashMap<>();
        params.put("userIdList",userIdList);
        params.put("courierStationId",workingSchedule.getCourierStationId());
        params.put("serviceBeginTime",workingSchedule.getServiceBeginTime());
        params.put("serviceEndTime",workingSchedule.getServiceEndTime());
        params.put("scheduleTime",workingSchedule.getScheduleTime());
        return workingScheduleUserService.findWorkingUser(params);
    }

    //组装值班人信息
    private WorkingScheduleUser buildWorkingScheduleUser(Map<String, List<UserGuideProjectVo>> userMap, WorkingSchedule workingSchedule, String userName, int type) {
        if(!userMap.containsKey(userName)){
            throw new CommonException("不存在该用户" + userName);
        }
        UserGuideProjectVo user = userMap.get(userName).get(0);
        WorkingScheduleUser scheduleUser = new WorkingScheduleUser();
        scheduleUser.setWorkingScheduleId(workingSchedule.getId());
        scheduleUser.setType(type);
        scheduleUser.setUserId(user.getId());
        scheduleUser.setUserName(user.getUserName());
        scheduleUser.setStatus(AuditStatusEnums.to_audit.getState());
        scheduleUser.setReserveStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());
        scheduleUser.setGuideProject(StringUtils.isEmpty(user.getInstructorGuideProject()) ? user.getGuideProject() : user.getInstructorGuideProject());
        scheduleUser.setCertificateGrade(user.getCertificateGrade());
        scheduleUser.setTaskNo(DateUtil.getNewTime());
        return scheduleUser;
    }

    /**
     * @param monthData
     * @param userId
     * @return
     * @author chenwf
     * @desc 获取当前用户月份排班信息
     * @date 2021-01-19 09:47:02
     */
    @Override
    public List<WorkingUserInfoVo> getTaskInfoList(String monthData, int activityType, Long userId) {
        List<WorkingUserInfoVo> workingUserInfoVos = new ArrayList<>();
        if(StringUtils.isEmpty(monthData)){
            monthData = DateUtil.format(new Date(),DateUtil.YYYY_MM);
        }
        Date scheduleDate = DateUtil.parse(monthData,DateUtil.YYYY_MM);
        //查找当月的值班任务
        Map<String,Object> params = new HashMap<>();
        params.put("scheduleBeginTime",DateUtil.beginOfMonth(scheduleDate).getTime());
        params.put("scheduleEndTime",DateUtil.endOfMonth(scheduleDate).getTime());
        params.put("userId",userId);
        params.put("activityType",activityType);
        List<WorkingUserVo> workingUserVos = this.dao.getTaskInfoList(params);
        if(!workingUserVos.isEmpty()) {
            Map<String, List<WorkingUserVo>> map = workingUserVos.stream().collect(Collectors.groupingBy(s -> s.getScheduleTimeText()));
            for (Map.Entry<String, List<WorkingUserVo>> entry : map.entrySet()) {
                WorkingUserInfoVo vo = new WorkingUserInfoVo();
                vo.setScheduleTimeText(entry.getKey());
                vo.setWorkingUserVos(entry.getValue());
                for (WorkingUserVo workingUserVo : vo.getWorkingUserVos()) {
                    Long serviceBeginTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceBeginTime());
                    Long serviceEndTime = workingUserVo.getScheduleTime() + DateUtil.timeToUnix(workingUserVo.getServiceEndTime());
                    //获取打卡状态
                    int signStatus = commonService.getPunchCardStatus(serviceBeginTime,serviceEndTime,workingUserVo.getSignInTime(),workingUserVo.getSignOutTime());
                    workingUserVo.setSignStatus(signStatus);
                }
                //查看是否存在异常打卡
                long abnormalCount = vo.getWorkingUserVos().stream().filter(s -> s.getSignStatus() == SignStatusEnums.SIGN_ABNORMAL.getStatus()).count();
                if(abnormalCount > 0){//存在异常打卡
                    vo.setStatus(SignStatusEnums.SIGN_ABNORMAL.getStatus());
                }else{
                    long unFinishCount = vo.getWorkingUserVos().stream().filter(s -> s.getSignStatus() == SignStatusEnums.SIGN_UN_FINISH.getStatus()).count();
                    if(unFinishCount > 0) {//存在待完成打卡
                        vo.setStatus(SignStatusEnums.SIGN_UN_FINISH.getStatus());
                    }else{//及不存在异常也不存在待完成打卡就是已完成
                        vo.setStatus(SignStatusEnums.SIGN_NORMAL.getStatus());
                    }
                }
                workingUserInfoVos.add(vo);
            }
        }
        return workingUserInfoVos;
    }

    /**
     * @param workingScheduleUserId
     * @param userId
     * @param activityType
     * @return
     * @author chenwf
     * @desc 获取值班任务信息(值班打卡)
     * @date 2021-01-19 09:47:02
     */
    @Override
    public WorkingUserVo getTaskInfo(Long workingScheduleUserId, Long userId, int activityType) {
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingScheduleUserId);
        params.put("userId",userId);
        params.put("activityType",activityType);
        List<WorkingUserVo> workingUserVos = this.dao.getTaskInfoList(params);
        if(!workingUserVos.isEmpty()){
            WorkingUserVo v = workingUserVos.get(0);
            List<WorkingSignRecord> signRecords = workingSignRecordService.getSignRecords(workingScheduleUserId);
            v.setSignRecords(signRecords);
            List<WorkingReplaceCard> replaceCards = workingReplaceCardService.getReplaceCardList(workingScheduleUserId);
            v.setReplaceCards(replaceCards);
            //签到是否异常：Ex 8-18  时间 >8 || 时间 > 18+2 都属于异常打卡
            Integer signInStatus = SignStatusEnums.SIGN_NORMAL.getStatus();
            Integer signOutStatus = SignStatusEnums.SIGN_NORMAL.getStatus();
            Long serviceBeginTime = v.getScheduleTime() + DateUtil.timeToUnix(v.getServiceBeginTime());
            Long serviceEndTime = v.getScheduleTime() + DateUtil.timeToUnix(v.getServiceEndTime()) + DateUtil.TWO_HOURS;
            if((v.getSignInTime() == null && System.currentTimeMillis() > serviceBeginTime)
                    || (v.getSignInTime() != null && v.getSignInTime() > serviceBeginTime)){
                signInStatus = SignStatusEnums.SIGN_ABNORMAL.getStatus();
            }

            if((v.getSignOutTime() == null && System.currentTimeMillis() > serviceEndTime)
                    || (v.getSignOutTime() != null && v.getSignOutTime() > serviceEndTime)){
                signOutStatus = SignStatusEnums.SIGN_ABNORMAL.getStatus();
            }
            v.setSignInStatus(signInStatus);
            v.setSignOutStatus(signOutStatus);
            return v;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer reservation(ActivityReservationVo reservationVo, UserEntity userEntity) {
        if (reservationVo.getReservationDate()==null||reservationVo.getRecruitDetailIds()==null){
            throw new CommonException("预约失败,参数有误!");
        }
        Instructor instructor=instructorService.findInstructorByUserId(userEntity.getId());
        if(instructor==null){
            throw new CommonException("预约失败,未绑定指导员信息!!");
        }
        String[] recruitDetailIds=reservationVo.getRecruitDetailIds().split(",");
        //活动招募详情
        ActivityRecruitInfo activityRecruitInfo;
        //活动招募时段明细
        ActivityRecruitDetail activityRecruitDetail;
        List<WorkingSchedule> workingSchedules;
        //可以一次预约多个时段(时段id用','分割)
        int count=0;
        for(String detailId:recruitDetailIds){
            //获取预约信息详情
            activityRecruitDetail=activityRecruitDetailService.get(Long.valueOf(detailId));
            if(activityRecruitDetail!=null){
                if(activityRecruitDetail.getHadRecruitNumber()>=activityRecruitDetail.getRecruitNumber()){
                    throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,预约人数已满!");
                }
                if(!activityRecruitDetail.getServiceDate().equals(reservationVo.getReservationDate())){
                    throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,预约日期和预约时段不匹配!");
                }
                Map<String,Object> map=new HashMap<>();
                map.put("userId",userEntity.getId());
                map.put("scheduleTime",reservationVo.getReservationDate());
                map.put("serviceBeginTime",activityRecruitDetail.getServiceStartTime());
                map.put("serviceEndTime",activityRecruitDetail.getServiceEndTime());
                //查询当前是否存在时间重合的预约记录
                List<String> userNames=workingScheduleUserService.findWorkingUser(map);
                if (userNames.size()>0){
                    throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,您预约活动和已预约活动时间存在重复!");
                }
            }else {
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,招募信息无效!");
            }
            activityRecruitInfo=activityRecruitInfoService.get(activityRecruitDetail.getRecruitId());
            if(activityRecruitInfo!=null){
                if(activityRecruitInfo.getRecruitStartDate()>System.currentTimeMillis()){
                    throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,活动招募未开始!");
                }
                if(activityRecruitInfo.getRecruitEndDate()+24*60*60*1000-1<System.currentTimeMillis()){
                    throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,活动招募已结束!");
                }
            }else {
                throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,获取招募信息失败!");
            }
            Map<String,Object> workingMap=new HashMap<>();
            workingMap.put("activityId",activityRecruitInfo.getId());
            workingMap.put("activityDetailId",activityRecruitDetail.getId());
            workingSchedules=this.dao.findByLimit(workingMap);
            Long workingScheduleId;
            if(workingSchedules.size()==0){
                //保存排班信息
                WorkingSchedule workingSchedule=new WorkingSchedule();
                workingSchedule.setActivityId(activityRecruitInfo.getId());
                workingSchedule.setActivityTitle(activityRecruitInfo.getTitle());
                workingSchedule.setActivityDetailId(activityRecruitDetail.getId());
                workingSchedule.setActivityType(activityRecruitInfo.getRecruitType());
                workingSchedule.setActivityAddress(activityRecruitInfo.getAddress());
                if(activityRecruitInfo.getRecruitType()==RecruitTypeEunms.STATION_RECRUIT.getType()){
                    workingSchedule.setCourierStationId(activityRecruitInfo.getCourierStationId());
                    workingSchedule.setCourierStationName(activityRecruitInfo.getCourierStation());
                }
                workingSchedule.setScheduleTime(activityRecruitDetail.getServiceDate());
                workingSchedule.setServiceBeginTime(activityRecruitDetail.getServiceStartTime());
                workingSchedule.setServiceEndTime(activityRecruitDetail.getServiceEndTime());
                workingSchedule.setRemarks("活动预约记录");
                this.dao.insert(workingSchedule);
                workingScheduleId=workingSchedule.getId();
            }else {
                workingScheduleId=workingSchedules.get(0).getId();
            }
            WorkingScheduleUser workingScheduleUser=new WorkingScheduleUser();
            workingScheduleUser.setTaskNo(DateUtil.getNewTime());
            workingScheduleUser.setWorkingScheduleId(workingScheduleId);
            workingScheduleUser.setType(ScheduleUserTypeEnums.SUBSCRIBE_DUTY.getType());
            workingScheduleUser.setUserId(userEntity.getId());
            workingScheduleUser.setUserName(userEntity.getUserName());
            workingScheduleUser.setCertificateGrade(instructor.getCertificateGrade());
            workingScheduleUser.setGuideProject(instructor.getGuideProject());
            workingScheduleUser.setReserveStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());
            workingScheduleUser.setStatus(AuditStatusEnums.to_audit.getState());
            count=count+workingScheduleUserService.insert(workingScheduleUser);
            Reservation reservation=new Reservation();
            reservation.setRelevanceId(activityRecruitInfo.getId());
            reservation.setType(activityRecruitInfo.getRecruitType());
            reservation.setWorkingId(workingScheduleUser.getId());
            reservation.setTitle(activityRecruitInfo.getTitle());
            reservation.setImage(activityRecruitInfo.getImage());
            reservation.setStartTime(activityRecruitDetail.getServiceDate()
                    +DateUtil.timeToUnix(activityRecruitDetail.getServiceStartTime()));
            reservation.setEndTime(activityRecruitDetail.getServiceDate()
                    +DateUtil.timeToUnix(activityRecruitDetail.getServiceEndTime()));
            reservation.setAddress(activityRecruitInfo.getAddress());
            reservation.setLongitude(activityRecruitInfo.getLongitude());
            reservation.setLatitude(activityRecruitInfo.getLatitude());
            reservation.setUserId(userEntity.getId());
            reservation.setPhone(userEntity.getPhone());
            reservation.setStatus(ReserveStatusEnums.ALREADY_RESERVE.getState());
            reservationService.insert(reservation);
            //已预约数加一
            activityRecruitDetail.setHadRecruitNumber(activityRecruitDetail.getHadRecruitNumber()+1);
            activityRecruitDetailService.update(activityRecruitDetail);
        }
        if (count!=recruitDetailIds.length){
            throw new CommonException(ResponseCode.SERVER_ERROR,"预约失败,请稍后重试!");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer cancelReservation(Long id) {
        WorkingScheduleUser workingScheduleUser=workingScheduleUserService.get(id);
        if(workingScheduleUser==null || workingScheduleUser.getWorkingScheduleId()==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取预约信息失败!");
        }
        WorkingSchedule workingSchedule=dao.get(workingScheduleUser.getWorkingScheduleId());
        if(workingSchedule==null || workingSchedule.getActivityDetailId()==null){
            throw new CommonException(ResponseCode.SERVER_ERROR,"获取排班信息失败!");
        }
        ActivityRecruitDetail activityRecruitDetail=activityRecruitDetailService.get(workingSchedule.getActivityDetailId());
        if(System.currentTimeMillis()>activityRecruitDetail.getServiceDate()){
            throw new CommonException(ResponseCode.SERVER_ERROR,"活动当天不能取消预约!");
        }
        //已预约数减一
        activityRecruitDetail.setHadRecruitNumber(activityRecruitDetail.getHadRecruitNumber()-1);
        activityRecruitDetailService.update(activityRecruitDetail);
        //设置状态取消预约
        workingScheduleUser.setReserveStatus(ReserveStatusEnums.CANCEL_RESERVE.getState());
        Map<String,Object> map=new HashMap<>();
        map.put("workingId",id);
        List<Reservation> reservations=reservationService.findByLimit(map);
        if(reservations!=null && reservations.size()>0){
            Reservation reservation=reservations.get(0);
            reservation.setStatus(ReserveStatusEnums.CANCEL_RESERVE.getState());
            reservationService.update(reservation);
        }
        return workingScheduleUserService.update(workingScheduleUser);
    }
}