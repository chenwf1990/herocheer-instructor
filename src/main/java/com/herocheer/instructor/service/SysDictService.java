package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysDict;
import com.herocheer.instructor.domain.vo.SysDictVO;

import java.util.List;

/**
 * @author gaorh
 * @desc 系统字典表(SysDict)表服务接口
 * @date 2021-01-07 17:18:16
 * @company 厦门熙重电子科技有限公司
 */
public interface SysDictService extends BaseService<SysDict, Long> {

    /**
     * 添加字典
     *
     * @param sysDictVO VO
     * @return {@link SysDict}
     */
    SysDict addDict(SysDictVO sysDictVO);

    /**
     * 通过id删除dict
     *
     * @param id id
     */
    void removeDictById(Long id);

    /**
     * 通过id查询dict
     *
     * @param id id
     * @return {@link SysDict}
     */
    SysDict findDictById(Long id);

    /**
     * 修改字典
     *
     * @param sysDictVO sys dict签证官
     * @return {@link SysDict}
     */
    SysDict modifyDict(SysDictVO sysDictVO);

    /**
     * 分页查询字典列表
     *
     * @param sysDictVO sys dict签证官
     * @return {@link Page<SysDict>}
     */
    Page<SysDict> findDictByPage(SysDictVO sysDictVO);

    /**
     * 查询字典名
     *
     * @param type 类型
     * @return {@link List<SysDict>}
     */
    List<SysDict> findDict(String type);

}