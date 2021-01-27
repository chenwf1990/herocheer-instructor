package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.SysOperationLogDao;
import com.herocheer.instructor.domain.entity.SysOperationLog;
import com.herocheer.instructor.domain.vo.SysOperationLogVO;
import com.herocheer.instructor.service.SysOperationLogService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaorh
 * @desc 后台用户操作日志表，如：后台登录、指导员入驻(SysOperationLog)表服务实现类
 * @date 2021-01-25 11:11:06
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class SysOperationLogServiceImpl extends BaseServiceImpl<SysOperationLogDao, SysOperationLog, Long> implements SysOperationLogService {

    /**
     * 添加系统操作日志
     *
     * @param sysOperationLogVO VO
     * @return {@link SysOperationLog}
     */
    @Override
    public SysOperationLog addSysOperationLog(SysOperationLogVO sysOperationLogVO) {
        SysOperationLog sysOperationLog = SysOperationLog.builder().build();
        BeanCopier.create(sysOperationLogVO.getClass(),sysOperationLog.getClass(),false).copy(sysOperationLogVO,sysOperationLog,null);
        this.insert(sysOperationLog);
        return sysOperationLog;
    }

    /**
     * 通过id删除系统操作日志
     *
     * @param id id
     */
    @Override
    public void removeSysOperationLogById(Long id) {
        this.delete(id);
    }

    /**
     * 系统操作日志列表
     *
     * @param sysOperationLogVO VO
     * @return {@link Page < SysDept >}
     */
    @Override
    public Page<SysOperationLog> findSysOperationLogByPage(SysOperationLogVO sysOperationLogVO) {
        Page page = Page.startPage(sysOperationLogVO.getPageNo(), sysOperationLogVO.getPageSize());
        List<SysOperationLog> sysOperationLogList = this.dao.selectSysOperationLogByPage(sysOperationLogVO);
        page.setDataList(sysOperationLogList);
        return page;
    }
}