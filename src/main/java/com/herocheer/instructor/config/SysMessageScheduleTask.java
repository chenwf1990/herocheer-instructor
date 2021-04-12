package com.herocheer.instructor.config;

import com.alibaba.fastjson.JSON;
import com.herocheer.instructor.aspect.SysMessageEvent;
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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public void execute() throws Exception {
        log.debug("###### ScheduleTask.execute start....");
        List<String> list = new ArrayList<>();

        List<WorkingSchedulsUserVo> workList = workingScheduleUserService.findWorkingUserByCheck();
        // 采集系统消息
        for ( WorkingSchedulsUserVo SchedulsUser :workList){
            if(SchedulsUser.getActivityType().equals(2)){
                // TODO 注意重复插入
                SpringUtil.publishEvent(new SysMessageEvent(new SysMessageVO(SysMessageEnums.MATCH_TIME.getText(),SysMessageEnums.MATCH_TIME.getType(),SysMessageEnums.MATCH_TIME.getCode(),SchedulsUser.getId())));
                /*Page<SysMessage> sysMessagePage=  sysMessageService.findMessageByPage(SysMessageVO.builder().messageCode(SysMessageEnums.MATCH_TIME.getCode()).objectId(SchedulsUser.getId()).build());
                if(CollectionUtils.isEmpty(sysMessagePage.getDataList())){
                    // 赛事活动
                    SpringUtil.publishEvent(new SysMessageEvent(new SysMessageVO(SysMessageEnums.MATCH_TIME.getText(),SysMessageEnums.MATCH_TIME.getType(),SysMessageEnums.MATCH_TIME.getCode(),SchedulsUser.getId())));
                }*/
            }else {
                SpringUtil.publishEvent(new SysMessageEvent(new SysMessageVO(SysMessageEnums.STATION_TIME.getText(),SysMessageEnums.STATION_TIME.getType(),SysMessageEnums.STATION_TIME.getCode(),SchedulsUser.getId())));

                /*Page<SysMessage> sysMessagePage=  sysMessageService.findMessageByPage(SysMessageVO.builder().messageCode(SysMessageEnums.STATION_TIME.getCode()).objectId(SchedulsUser.getId()).build());
                if(CollectionUtils.isEmpty(sysMessagePage.getDataList())){
                    // 驿站值班
                }*/
            }
        }
        log.debug("###### ScheduleTask.execute end...." + JSON.toJSONString(""));
    }
}
