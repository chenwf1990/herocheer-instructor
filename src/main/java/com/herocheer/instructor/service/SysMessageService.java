package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysMessage;
import com.herocheer.instructor.domain.vo.SysMessageVO;

import java.util.List;

/**
 * @author gaorh
 * @desc 系统消息通知(SysMessage)表服务接口
 * @date 2021-04-01 17:17:47
 * @company 厦门熙重电子科技有限公司
 */
public interface SysMessageService extends BaseService<SysMessage, Long> {

    /**
     * 添加消息
     *
     * @param sysMessageVO VO
     */
    void addMessage(SysMessageVO sysMessageVO);

    /**
     * 通过id修改消息读取状态
     *
     * @param id id
     * @return int
     */
    int modifyMessageReadStatusById(Long id);

    /**
     * 通过id修改消息处理状态
     *
     * @param id id
     * @return int
     */
    int modifyMessageHandleStatusById(Long id);

    /**
     * 消息列表
     *
     * @param sysMessageVO VO
     * @return {@link Page<SysMessage>}
     */
    Page<SysMessage> findMessageByPage(SysMessageVO sysMessageVO);

    /**
     * 统计信息
     *
     * @return int
     */
    int countMessage();

    /**
     * 同步消息中心信息
     *
     * @param messageCode messageCode
     * @param objId id
     */
    void modifyMessage(List<String> messageCode, Long objId, Boolean handleStatus, Boolean readStatus);

    /**
     * 找到一个系统消息
     *
     * @param messageCode 消息代码
     * @param objId       obj id
     * @return {@link SysMessage}
     */
    SysMessage findMessageOne(List<String> messageCode, Long objId);
}