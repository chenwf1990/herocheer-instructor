package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 排班表
 * @date 2021-01-21 11:01:00
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class WorkingSchedule extends BaseEntity {
    @ApiModelProperty("活动id")   
    private Long activityId;
    @ApiModelProperty("活动明细")   
    private Long activityDetailId;
    @ApiModelProperty("活动类型 1驿站 2赛事")   
    private Integer activityType;
    @ApiModelProperty("驿站id")   
    private Long courierStationId;
    @ApiModelProperty("驿站名称")   
    private String courierStationName;
    @ApiModelProperty("排班日期")   
    private Long scheduleTime;
    @ApiModelProperty("服务时段id")   
    private Long serviceTimeId;
    @ApiModelProperty("服务开始时段 (时分)")   
    private String serviceBeginTime;
    @ApiModelProperty("服务结束时段 (时分)")   
    private String serviceEndTime;
    @ApiModelProperty("备注")   
    private String remarks;

}