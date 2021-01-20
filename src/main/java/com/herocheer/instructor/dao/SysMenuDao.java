package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysMenu;
import com.herocheer.instructor.domain.vo.OptionTreeVO;
import com.herocheer.instructor.domain.vo.SysMenuVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 菜单表(SysMenu)表数据库访问层
 * @date 2021-01-07 17:07:30
 * @company 厦门熙重电子科技有限公司
 */
public interface SysMenuDao extends BaseDao<SysMenu, Long> {

    /**
     * 用户选择菜单树
     *
     * @param map 地图
     * @return {@link List<SysMenu>}
     */
    List<OptionTreeVO> selectMenuTreeToUser(Map<String, Object> map);

    /**
     * 用户选择菜单树
     *
     * @param map 地图
     * @return {@link List<SysMenu>}
     */
    List<SysMenu> selectMenuTreeToRole(Map<String, Object> map);

    /**
     * 选择菜单的页面
     * 分页查询菜单
     *
     * @param sysMenuVO 系统菜单签证官
     * @return {@link List<SysMenu>}
     */
    List<SysMenu> selectMenuByPage(SysMenuVO sysMenuVO);
}