package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.CourierStationDao;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.domain.vo.CourierStationQueryVo;
import com.herocheer.instructor.domain.vo.CourierStationVo;
import com.herocheer.instructor.service.ActivityRecruitInfoService;
import com.herocheer.instructor.service.CourierStationService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  驿站(CourierStation)表服务实现类
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class CourierStationServiceImpl extends BaseServiceImpl<CourierStationDao, CourierStation,Long> implements CourierStationService {


    @Resource
    private ActivityRecruitInfoService activityRecruitInfoService;
    /**
     * @param courierStation
     * @return
     * @author chenwf
     * @desc 新增驿站
     * @date 2021-01-07 17:26:18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
        Page page = Page.startPage(courierStationQueryVo.getPageNo(),courierStationQueryVo.getPageSize());
        List<CourierStationVo> courierStations = this.dao.queryPageList(courierStationQueryVo);
        page.setDataList(courierStations);
        return page;
    }

    @Override
    public Integer deleteCourierStation(Long id) {
        Map<String,Object> map=new HashMap<>();
        map.put("courierStationId",id);
        List<ActivityRecruitInfo> list=activityRecruitInfoService.findByLimit(map);
        if (list!=null || list.size()>0){
            throw new CommonException(ResponseCode.SERVER_ERROR, "驿站有发布招募信息无法删除!");
        }
        return dao.delete(id);
    }
}