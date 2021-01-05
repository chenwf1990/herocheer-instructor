package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.dao.CourierStationDao;
import com.herocheer.instructor.service.CourierStationService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenwf
 * @desc  驿站(CourierStation)表服务实现类
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class CourierStationServiceImpl extends BaseServiceImpl<CourierStationDao, CourierStation,Long> implements CourierStationService {
    
}