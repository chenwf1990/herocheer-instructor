package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.ServiceHours;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author makejava
 * @desc  驿站服务时段 (ServiceHours)表数据库访问层
 * @date 2021-01-05 11:32:26
 * @company 厦门熙重电子科技有限公司
 */
public interface ServiceHoursDao extends BaseDao<ServiceHours,Long>{

    /**
     * 查询驿站服务时段列表
     * @param queryVo
     * @return
     */
    List<ServiceHours> queryList(ServiceHoursQueryVo queryVo);

}