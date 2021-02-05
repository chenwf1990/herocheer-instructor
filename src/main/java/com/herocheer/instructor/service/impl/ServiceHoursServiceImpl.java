package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.ServiceHours;
import com.herocheer.instructor.dao.ServiceHoursDao;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.service.ServiceHoursService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author makejava
 * @desc  驿站服务时段 (ServiceHours)表服务实现类
 * @date 2021-01-05 11:32:26
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class ServiceHoursServiceImpl extends BaseServiceImpl<ServiceHoursDao, ServiceHours,Long> implements ServiceHoursService {

    @Override
    public Page<ServiceHours> queryPageList(ServiceHoursQueryVo queryVo) {
        Page page=Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        List<ServiceHours> serviceHoursList=dao.queryList(queryVo);
        page.setDataList(serviceHoursList);
        return page;
    }

    @Override
    public List<ServiceHours> getHoursByStationId(Long stationId) {
        ServiceHoursQueryVo queryVo=new ServiceHoursQueryVo();
        queryVo.setStationId(stationId);
        List<ServiceHours> serviceHoursList=dao.queryList(queryVo);
        return serviceHoursList;
    }
}