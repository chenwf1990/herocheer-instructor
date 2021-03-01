package com.herocheer.instructor.service;

import cn.hutool.core.lang.tree.Tree;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.vo.AreaQueryVo;
import com.herocheer.instructor.domain.vo.AreaVO;

import java.awt.geom.Area;
import java.util.List;

/**
 * @author chenwf
 * @desc  区域管理（省市区街道社区）(SysArea)表服务接口
 * @date 2021-01-07 09:50:58
 * @company 厦门熙重电子科技有限公司
 */
public interface SysAreaService extends BaseService<SysArea,Long> {
    /**
     * @author chenwf
     * @desc  获取所有区域，树形态
     * @date 2021-01-07 09:50:58
     * @param type 1无权限 2有数据权限
     * @return
     */
    List<Tree<Long>> getAllArea(int type);

    /**
     * @author chenwf
     * @desc  获取所有区域，树形态
     * @date 2021-01-07 09:50:58
     * @param type 1无权限 2有数据权限
     * @return
     */
    List<Tree<Long>> getAllArea(int type,Long parentId);

    /**
     * @author chenwf
     * @desc  区域列表查询
     * @date 2021-01-07 09:50:58
     * @param areaQueryVo
     * @return
     */
    Page<SysArea> queryPageList(AreaQueryVo areaQueryVo);

    /**
     * 通过id查找子区域
     *
     * @param areaQueryVo 区域查询签证官
     * @return {@link Page<SysArea>}
     */
    Page<SysArea> findAreaById(AreaQueryVo areaQueryVo);

    /**
     * 找到区域节点
     *
     * @param id id
     * @return {@link List<String>}
     */
    List<String> findAreaNode(Long id);

    /**
     * 添加区域
     *
     * @param areaVO VO
     * @return {@link Area}
     */
    SysArea addArea(AreaVO areaVO);
}