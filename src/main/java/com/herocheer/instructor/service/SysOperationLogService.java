package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysDept;
import com.herocheer.instructor.domain.entity.SysOperationLog;
import com.herocheer.instructor.domain.vo.SysOperationLogVO;

/**
 * @author gaorh
 * @desc 后台用户操作日志表，如：后台登录、指导员入驻(SysOperationLog)表服务接口
 * @date 2021-01-25 11:11:06
 * @company 厦门熙重电子科技有限公司
 */
public interface SysOperationLogService extends BaseService<SysOperationLog, Long> {


    /**
     * 添加系统操作日志
     *
     * @param sysOperationLogVO VO
     * @return {@link SysOperationLog}
     */
    SysOperationLog addSysOperationLog(SysOperationLogVO sysOperationLogVO);

    /**
     * 通过id删除系统操作日志
     *
     * @param id id
     */
    void removeSysOperationLogById(Long id);

    /**
     * 系统操作日志列表
     *
     * @param sysOperationLogVO VO
     * @return {@link Page<SysDept>}
     */
    Page<SysOperationLog>  findSysOperationLogByPage(SysOperationLogVO sysOperationLogVO);
}
