package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysOperation;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 功能操作表(SysOperation)表数据库访问层
 * @date 2021-01-07 17:46:31
 * @company 厦门熙重电子科技有限公司
 */
public interface SysOperationDao extends BaseDao<SysOperation, Long> {

    /**
     * 选择一个操作
     *
     * @param map 地图
     * @return int
     */
    int selectSysOperateOne(Map<String, Object> map);

    /**
     * 通过pid选择操作
     *
     * @return {@link List<SysOperation>}
     */
    List<SysOperation> selectOperationByPid(Map<String, Object> map);
}