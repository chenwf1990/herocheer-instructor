package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysDict;
import com.herocheer.instructor.domain.vo.SysDictVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 系统字典表(SysDict)表数据库访问层
 * @date 2021-01-07 17:18:16
 * @company 厦门熙重电子科技有限公司
 */
public interface SysDictDao extends BaseDao<SysDict, Long> {

    /**
     * 字典列表
     *
     * @param sysDictVO sys dict签证官
     * @return {@link List<SysDict>}
     */
    List<SysDict> selectDictByPage(SysDictVO sysDictVO);


    int selectSysDictOne(Map<String, Object> map);
}