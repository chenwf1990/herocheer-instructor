package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.WorkingScheduleDao;
import com.herocheer.instructor.domain.entity.*;
import com.herocheer.instructor.domain.vo.*;
import com.herocheer.instructor.enums.AuditStatusEnums;
import com.herocheer.instructor.enums.ScheduleUserTypeEnums;
import com.herocheer.instructor.enums.SignStatusEnums;
import com.herocheer.instructor.service.*;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.instructor.utils.ExcelUtils;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenwf
 * @desc  排班表(WorkingSchedule)表服务实现类
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
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
    public void addWorkingScheduls(List<WorkingVo> workingScheduls) {
        if(workingScheduls.isEmpty()){
            return;
        }
        //判断是否存在同个时段相同人员
        isSameUser(workingScheduls);
        for (WorkingVo workingVo : workingScheduls) {
            this.dao.insert(workingVo);
            List<WorkingScheduleUser> workingScheduleUsers = workingVo.getWorkingScheduleUsers();
            //设置id
            workingScheduleUsers.forEach(w -> {
                w.setWorkingScheduleId(workingVo.getId());
                if(w.getType() == ScheduleUserTypeEnums.SUBSCRIBE_DUTY.getType()){
                    w.setStatus(AuditStatusEnums.to_audit.getState());
                }
            });
            //批量插入值班人员信息
            isSameTimeWorkingUser(workingVo,workingScheduleUsers);
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
            throw new CommonException(sameList.stream().collect(Collectors.joining("\n")) + "\n存在重复班次");
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
        //过滤存在id的值班人员，更新没意义
        isSameTimeWorkingUser(workingVo,workingScheduleUsers);
        workingScheduleUsers.forEach(u ->{
            if(u.getId() == null){
                workingScheduleUserService.insert(u);
            }else{
                workingScheduleUserService.update(u);
            }
        });
    }

    //判断传送的值班人员是否存在相同时间段的值班人员（数据库查询）
    private void isSameTimeWorkingUser(WorkingSchedule workingVo, List<WorkingScheduleUser> workingScheduleUsers) {
        //判断值班人员是否存在同个时间段的值班
        List<Long> userIdList = workingScheduleUsers.stream().map(s -> s.getUserId()).collect(Collectors.toList());
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
    public long batchDelete(String ids) {
        List<Long> idList = Stream.of(ids.split(",")).map(s -> Long.parseLong(s)).collect(Collectors.toList());
        return this.dao.batchDelete(idList);
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
        JSONArray jsonArray = JSONObject.parseArray(serviceTimes);
        String times = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            if(json.isEmpty()){
                continue;
            }
            times = json.getString("startTime") + "~" + json.getString("endTime") + ",";
        }
        // 准备将数据集合封装成Excel对象
        List<String> row1 = CollUtil.newArrayList("*所属驿站", "*排班日期", "*时段", "*值班站长","*固定值班人员（人员姓名请用逗号隔开）");
        List<String> row2 = CollUtil.newArrayList(courierStation.getName(), "2020-01-01", "", "张三","李四，王五");
        List<List<String>> rows = CollUtil.newArrayList(row1,row2);
        ExcelWriter writer = ExcelUtil.getWriter(true);
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
            ExcelReader reader = ExcelUtil.getReader(multipartFile.getInputStream());
            //第一行是标题，第二行是标
            List<List<Object>> read = reader.read(1,1000);
            List<Object> title = read.get(0);
            //获取excel所有排班人员信息
            String userNames = "";
            List<String> list = new ArrayList<>();
            for (int i = 1; i < read.size(); i++) {//0是标题，数据行从1开始
                List<Object> data = read.get(i);
                for (int j = 0; j < data.size(); j++) {
                    boolean required = ExcelUtils.isRequired(title.get(j), data.get(j));
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
            List<User> users = userService.findUserByUserNames(userNameList);
            //查找到的用户信息按用户名称进行聚合，后续根据用户名key查找用户信息velue
            Map<String, List<User>> userMap = users.stream().collect(Collectors.groupingBy(User::getUserName));
            for (int i = 1; i < read.size(); i++) {//0是标题，数据行从1开始
                List<Object> dataList = read.get(i);
                buildSaveWorkingSchedule(courierStation,serviceHours,dataList,userMap,i);
            }
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }

    }

    //组装、保存值班记录
    private void buildSaveWorkingSchedule(CourierStation courierStation, ServiceHours serviceHours, List<Object> dataList, Map<String, List<User>> userMap, int i) {
        WorkingSchedule workingSchedule = new WorkingSchedule();
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
    private WorkingScheduleUser buildWorkingScheduleUser(Map<String, List<User>> userMap, WorkingSchedule workingSchedule, String userName, int type) {
        if(!userMap.containsKey(userName)){
            throw new CommonException("不存在该用户" + userName);
        }
        User user = userMap.get(userName).get(0);
        WorkingScheduleUser scheduleUser = new WorkingScheduleUser();
        scheduleUser.setWorkingScheduleId(workingSchedule.getId());
        scheduleUser.setType(type);
        scheduleUser.setUserId(user.getId());
        scheduleUser.setUserName(user.getUserName());
        scheduleUser.setStatus(AuditStatusEnums.to_audit.getState());
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
    public List<WorkingUserInfoVo> getUserWorkingList(String monthData, Long userId) {
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
        List<WorkingUserVo> workingUserVos = this.dao.getUserWorkingList(params);
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
     * @return
     * @author chenwf
     * @desc 获取值班任务信息(值班打卡)
     * @date 2021-01-19 09:47:02
     */
    @Override
    public WorkingUserVo getTaskInfo(Long workingScheduleUserId, Long userId) {
        Map<String,Object> params = new HashMap<>();
        params.put("workingScheduleUserId",workingScheduleUserId);
        params.put("userId",userId);
        List<WorkingUserVo> workingUserVos = this.dao.getUserWorkingList(params);
        if(!workingUserVos.isEmpty()){
            WorkingUserVo v = workingUserVos.get(0);
            Map<String,Object> signMap = new HashMap<>();
            signMap.put("workingScheduleUserId",workingScheduleUserId);
            signMap.put("orderBy","signTime");
            List<WorkingSignRecord> signRecords = workingSignRecordService.findByLimit(signMap);
            v.setSignRecords(signRecords);
            return v;
        }
        return null;
    }
}