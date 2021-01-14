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
@ApiModel("排班列表查询实体")
public class WorkingScheduleQueryVo {
    @ApiModelProperty("驿站id")
    private Long courierStationId;
    @ApiModelProperty("值班人员姓名")
    private String userName;
    @ApiModelProperty("值班开始日期")
    private Long scheduleBeginTime;
    @ApiModelProperty("值班结束日期")
    private Long scheduleEndTime;
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("页数")
    private int pageSize;
}
