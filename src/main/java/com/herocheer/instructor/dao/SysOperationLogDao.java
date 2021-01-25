package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysOperationLog;
import com.herocheer.instructor.domain.vo.SysOperationLogVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author gaorh
 * @desc 后台用户操作日志表，如：后台登录、指导员入驻(SysOperationLog)表数据库访问层
 * @date 2021-01-25 11:11:06
 * @company 厦门熙重电子科技有限公司
 */
public interface SysOperationLogDao extends BaseDao<SysOperationLog, Long> {

    /**
     * 系统操作日志列表（分页）
     *
     * @param sysOperationLogVO VO
     * @return {@link List<SysOperationLog>}
     */
    List<SysOperationLog> selectSysOperationLogByPage(SysOperationLogVO sysOperationLogVO);
}