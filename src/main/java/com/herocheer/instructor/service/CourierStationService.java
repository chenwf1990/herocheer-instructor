package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.CourierStationQueryVo;

/**
 * @author chenwf
 * @desc  驿站(CourierStation)表服务接口
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
public interface CourierStationService extends BaseService<CourierStation,Long> {
    /**
     * @author chenwf
     * @desc  新增驿站
     * @date 2021-01-07 17:26:18
     * @param courierStation
     * @return
     */
    Long addCourierStation(CourierStation courierStation);

    /**
     * @author chenwf
     * @desc  驿站列表查询
     * @date 2021-01-07 17:26:18
     * @param courierStationQueryVo
     * @return
     */
    Page<CourierStation> queryPageList(CourierStationQueryVo courierStationQueryVo);
}