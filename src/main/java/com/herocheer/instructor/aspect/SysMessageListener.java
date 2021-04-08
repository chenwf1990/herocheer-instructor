package com.herocheer.instructor.aspect;

import com.herocheer.instructor.domain.vo.SysMessageVO;
import com.herocheer.instructor.service.SysMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步监听消息事件
 *
 * @author gaorh
 * @create 2021-04-02
 */
@Slf4j
@Component
public class SysMessageListener {

    @Autowired
    private SysMessageService sysMessageService;

    @Async
    @EventListener(SysMessageEvent.class)
    public void saveSysMessage(SysMessageEvent event) {
        sysMessageService.addMessage((SysMessageVO)event.getSource());
    }
}
