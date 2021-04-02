package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.SysMessageDao;
import com.herocheer.instructor.domain.entity.SysMessage;
import com.herocheer.instructor.domain.vo.SysMessageVO;
import com.herocheer.instructor.service.SysMessageService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 系统消息通知(SysMessage)表服务实现类
 * @date 2021-04-01 17:17:47
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessageDao, SysMessage, Long> implements SysMessageService {

    /**
     * 添加消息
     *
     * @param sysMessageVO VO
     * @return {@link SysMessage}
     */
    // TODO 异步且线程池、事件
    @Override
    public SysMessage addMessage(SysMessageVO sysMessageVO) {
        SysMessage sysMessage = SysMessage.builder().build();
        BeanCopier.create(sysMessageVO.getClass(),sysMessage.getClass(),false).copy(sysMessageVO,sysMessage,null);
        this.insert(sysMessage);
        return sysMessage;
    }

    /**
     * 通过id修改消息读取状态
     *
     * @param id id
     * @return int
     */
    @Override
    public int modifyMessageReadStatusById(Long id) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("ReadStatus", true);
        return this.dao.updateMessageStatusById(paramMap);
    }

    /**
     * 通过id修改消息处理状态
     *
     * @param id id
     * @return int
     */
    @Override
    public int modifyMessageHandleStatusById(Long id) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("handleStatus", true);
        return this.dao.updateMessageStatusById(paramMap);
    }

    /**
     * 消息列表
     *
     * @param sysMessageVO VO
     * @return {@link Page <SysMessage>}
     */
    @Override
    public Page<SysMessage> findMessageByPage(SysMessageVO sysMessageVO) {
        Page page = Page.startPage(sysMessageVO.getPageNo(), sysMessageVO.getPageSize());
        List<SysMessage> sysMessageList = this.dao.selectMessageByPage(sysMessageVO);
        page.setDataList(sysMessageList);
        return page;
    }

    /**
     * 统计信息
     *
     * @return int
     */
    @Override
    public int countMessage() {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ReadStatus", false);
        return this.dao.countMessage(paramMap);
    }
}