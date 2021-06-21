package com.herocheer.instructor.service;

import cn.hutool.core.lang.tree.Tree;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysMenu;
import com.herocheer.instructor.domain.vo.OptionTreeVO;
import com.herocheer.instructor.domain.vo.SysMenuVO;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 菜单表(SysMenu)表服务接口
 * @date 2021-01-07 17:07:30
 * @company 厦门熙重电子科技有限公司
 */
public interface SysMenuService extends BaseService<SysMenu, Long> {

    /**
     * 根据用户的菜单权限
     *
     * @param currentUser 当前用户
     * @return {@link List<Tree<Long>>}
     */
    List<Tree<Long>> findMenuTreeToUser(UserEntity currentUser);

    /**
     * 菜单树
     *
     * @param id id
     * @return {@link OptionTreeVO}
     */
    OptionTreeVO findMenuTreeToRole(Long id,Integer mark);

    /**
     * 添加菜单
     *
     * @param sysMenuVO VO
     * @return {@link SysMenu}
     */
    SysMenu addMenu(SysMenuVO sysMenuVO);

    /**
     * 通过id找到菜单
     *
     * @param id id
     * @return {@link SysMenuVO}
     */
    SysMenuVO findMenuById(Long id);

    /**
     * 通过id修改菜单
     *
     * @param sysMenuVO 系统菜单签证官
     * @return {@link SysMenu}
     */
    SysMenu modifyMenuById(SysMenuVO sysMenuVO);

    /**
     * 通过id删除菜单
     *
     * @param id id
     */
    void removeMenuById(Long id);

    /**
     * 分页查询菜单列表
     *
     * @param sysMenuVO VO
     * @return {@link Page<SysMenu>}
     */
    Page<SysMenu> findMenuByPage(SysMenuVO sysMenuVO);

    /**
     * 根据用户角色获取权限
     *
     * @param map 地图
     * @return {@link List<SysMenu>}
     */
    List<SysMenu> findMenuTreeToRole(Map<String, Object> map);
}