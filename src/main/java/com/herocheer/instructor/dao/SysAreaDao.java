package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.vo.AreaQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}