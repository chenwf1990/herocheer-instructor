package com.herocheer.instructor.service;

import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysOperation;
import com.herocheer.instructor.domain.vo.SysOperationVO;

/**
 * @author gaorh
 * @desc 功能操作表(SysOperation)表服务接口
 * @date 2021-01-07 17:46:31
 * @company 厦门熙重电子科技有限公司
 */
public interface SysOperationService extends BaseService<SysOperation, Long> {

    /**
     * 添加操作
     *
     * @param sysOperationVO VO
     * @return {@link SysOperation}
     */
    SysOperation addOperation(SysOperationVO sysOperationVO);

    /**
     * 通过id移除操作
     *
     * @param id id
     */
    void removeOperationById(Long id);

    /**
     * 通过id查找操作
     *
     * @param id id
     * @return {@link SysOperation}
     */
    SysOperation findOperationById(Long id);

    /**
     * 修改操作
     *
     * @param sysOperationVO VO
     * @return {@link SysOperation}
     */
    SysOperation modifyOperation(SysOperationVO sysOperationVO);

}