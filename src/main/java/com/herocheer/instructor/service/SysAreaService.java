package com.herocheer.instructor.service;

import cn.hutool.core.lang.tree.Tree;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.vo.AreaQueryVo;
import org.apache.ibatis.annotations.Param;

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
}