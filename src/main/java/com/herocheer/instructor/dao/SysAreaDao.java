package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.entity.SysMenu;
import com.herocheer.instructor.domain.vo.AreaQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  区域管理（省市区街道社区）(SysArea)表数据库访问层
 * @date 2021-01-07 09:50:58
 * @company 厦门熙重电子科技有限公司
 */
public interface SysAreaDao extends BaseDao<SysArea,Long>{

    /**
     * @author chenwf
     * @desc  区域列表查询
     * @date 2021-01-07 09:50:58
     * @param areaQueryVo
     * @return
     */
    List<SysArea> queryPageList(AreaQueryVo areaQueryVo);

    /**
     * 根据用户角色获取数据权限
     *
     * @param map 地图
     * @return {@link List< SysMenu >}
     */
    List<SysArea> selectAreaTreeToRole(Map<String, Object> map);

    /**
     * 根据ID选择子层区域
     *
     * @param map 地图
     * @return {@link List<SysArea>}
     */
    List<SysArea> selectAreaById(Map<String, Object> map);

    /**
     * 选中的区域节点
     *
     * @param id id
     * @return {@link List<String>}
     */
    List<String> selectedAreaNode(Long id);
}