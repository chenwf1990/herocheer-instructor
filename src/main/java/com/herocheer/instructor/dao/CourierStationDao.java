package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.domain.vo.CourierStationQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author chenwf
 * @desc  驿站(CourierStation)表数据库访问层
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
public interface CourierStationDao extends BaseDao<CourierStation,Long>{
    /**
     * @author chenwf
     * @desc  驿站列表查询
     * @date 2021-01-07 17:26:18
     * @param courierStationQueryVo
     * @return
     */
    List<CourierStation> queryPageList(CourierStationQueryVo courierStationQueryVo);
}