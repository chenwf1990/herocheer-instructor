package com.herocheer.instructor.aspect;

import org.springframework.context.ApplicationEvent;

/**
 * 系统消息事件
 *
 * @author gaorh
 * @create 2021-04-02
 */
public class SysMessageEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public SysMessageEvent(Object source) {
        super(source);
    }
}
