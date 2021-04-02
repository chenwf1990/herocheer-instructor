package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysMessage;
import com.herocheer.instructor.domain.vo.SysMessageVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 系统消息通知(SysMessage)表数据库访问层
 * @date 2021-04-01 17:17:47
 * @company 厦门熙重电子科技有限公司
 */
public interface SysMessageDao extends BaseDao<SysMessage, Long> {

    /**
     * 通过id更新消息读取状态
     *
     * @return int
     */
    int updateMessageStatusById(Map<String,Object> paramMap);

    /**
     * 消息列表
     *
     * @param sysMessageVO VO
     * @return {@link List<SysMessage>}
     */
    List<SysMessage> selectMessageByPage(SysMessageVO sysMessageVO);


    /**
     * 统计信息
     *
     * @param paramMap 参数映射
     * @return int
     */
    int countMessage(Map<String,Object> paramMap);
}