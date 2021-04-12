package com.herocheer.instructor.config;

import com.alibaba.fastjson.JSON;
import com.herocheer.instructor.aspect.SysMessageEvent;
import com.herocheer.instructor.domain.entity.SysMessage;
import com.herocheer.instructor.domain.vo.SysMessageVO;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;
import com.herocheer.instructor.enums.SysMessageEnums;
import com.herocheer.instructor.service.SysMessageService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 系统消息定时任务
 *
 * @author gaorh
 * @create 2021-04-09
 */

@Component
@Configuration
@EnableScheduling
@Slf4j
public class SysMessageScheduleTask {

    @Autowired
    private WorkingScheduleUserService workingScheduleUserService;

    @Autowired
    private SysMessageService sysMessageService;
    //3.添加定时任务
//    @Scheduled(cron = "0/30 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
     @Scheduled(cron = "0 0 1 * * ?")
    //    每天凌晨1点执行一次：0 0 1 * * ?

    public void execute() throws Exception {
        log.debug("###### ScheduleTask.execute start....");
        //
        List<WorkingSchedulsUserVo> workList = workingScheduleUserService.findWorkingUserByCheck();
        // 采集系统消息
        for (WorkingSchedulsUserVo schedulsUser : workList){
            SysMessage sysMessage=  sysMessageService.findMessageOne(Arrays.asList(SysMessageEnums.MATCH_TIME.getCode(),SysMessageEnums.STATION_TIME.getCode()),schedulsUser.getId());
            if(ObjectUtils.isEmpty(sysMessage)){
                // 赛事活动
                if(schedulsUser.getActivityType().equals(2)){
                    SpringUtil.publishEvent(new SysMessageEvent(new SysMessageVO(SysMessageEnums.MATCH_TIME.getText(),SysMessageEnums.MATCH_TIME.getType(),SysMessageEnums.MATCH_TIME.getCode(),schedulsUser.getId())));
                }else {
                    // 驿站值班
                    SpringUtil.publishEvent(new SysMessageEvent(new SysMessageVO(SysMessageEnums.STATION_TIME.getText(),SysMessageEnums.STATION_TIME.getType(),SysMessageEnums.STATION_TIME.getCode(),schedulsUser.getId())));
                }
            }else {
                // 同步系统消息状态(不区别审核通过和驳回) 同一张表的ID不会重复
                if(sysMessage.getReadStatus().equals(true)){
                    sysMessageService.modifyMessage(Arrays.asList(SysMessageEnums.STATION_TIME.getCode(),SysMessageEnums.MATCH_TIME.getCode()), schedulsUser.getId(),false,false);
                }
            }
        }
        log.debug("###### ScheduleTask.execute end...." + JSON.toJSONString(workList));
    }
}
