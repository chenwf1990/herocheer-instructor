package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.ServiceHours;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;

import java.util.List;

/**
 * @author makejava
 * @desc  驿站服务时段 (ServiceHours)表服务接口
 * @date 2021-01-05 11:32:26
 * @company 厦门熙重电子科技有限公司
 */
public interface ServiceHoursService extends BaseService<ServiceHours,Long> {
    /**
     * 分页查询服务时间段列表
     * @param queryVo
     * @return
     */
    Page<ServiceHours> queryPageList(ServiceHoursQueryVo queryVo);

    /**
     * 根据驿站id查询服务时段列表
     * @param stationId
     * @return
     */
    List<ServiceHours> getHoursByStationId(Long stationId);
}