package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.ActivityRecruitDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  活动招募-明细(ActivityRecruitDetail)表服务接口
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
public interface ActivityRecruitDetailService extends BaseService<ActivityRecruitDetail,Long> {

    /**
     * 根据招募信息id删除招募详情
     * @param recruitId
     */
    void deleteDetailByRecruitId(Long recruitId);

    /**
     * 查询驿站招募详情
     */
    List<ActivityRecruitDetailVo> getRecruitHours(Long recruitId, Long serviceStartTime, Long serviceEndTime);
}