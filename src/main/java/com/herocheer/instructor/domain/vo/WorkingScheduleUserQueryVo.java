package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/12
 * @company 厦门熙重电子科技有限公司
 */
@Data
@ApiModel("值班人员列表查询实体")
public class WorkingScheduleUserQueryVo {
    @ApiModelProperty("驿站id")
    private Long courierStationId;
    @ApiModelProperty("值班人员姓名")
    private String userName;
    @ApiModelProperty("值班开始日期")
    private Long scheduleBeginTime;
    @ApiModelProperty("值班结束日期")
    private Long scheduleEndTime;
    @ApiModelProperty("活动类型 1驿站 2赛事")
    private Long activityType;
    @ApiModelProperty("任务编号")
    private String taskNo;
    @ApiModelProperty("审核状态 0不需要审核 1待审核 2已审核")
    private Integer status;
    @ApiModelProperty("打卡状态-0正常 1异常")
    private Integer signStatus;
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("页数")
    private int pageSize;
}
