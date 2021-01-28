package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.CourierStationDao;
import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.domain.vo.CourierStationQueryVo;
import com.herocheer.instructor.domain.vo.CourierStationVo;
import com.herocheer.instructor.service.CourierStationService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenwf
 * @desc  驿站(CourierStation)表服务实现类
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class CourierStationServiceImpl extends BaseServiceImpl<CourierStationDao, CourierStation,Long> implements CourierStationService {

    /**
     * @param courierStation
     * @return
     * @author chenwf
     * @desc 新增驿站
     * @date 2021-01-07 17:26:18
     */
    @Override
    public int addCourierStation(CourierStation courierStation) {
        return this.dao.insert(courierStation);
    }

    /**
     * @param courierStationQueryVo
     * @return
     * @author chenwf
     * @desc 驿站列表查询
     * @date 2021-01-07 17:26:18
     */
    @Override
    public Page<CourierStationVo> queryPageList(CourierStationQueryVo courierStationQueryVo) {
        courierStationQueryVo.setUserId(18L);
        Page page = Page.startPage(courierStationQueryVo.getPageNo(),courierStationQueryVo.getPageSize());
        List<CourierStationVo> courierStations = this.dao.queryPageList(courierStationQueryVo);
        page.setDataList(courierStations);
        return page;
    }
}