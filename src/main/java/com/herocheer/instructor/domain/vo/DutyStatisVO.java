package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 值班服务时长统计VO
 *
 * @author gaorh
 * @create 2021-06-09
 */
@ApiModel(description = "值班服务时长统计VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DutyStatisVO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("证书等级")
    private String certificateGrade;

    @ApiModelProperty("指导项目")
    private String guideProject;

    @ApiModelProperty("值班日期")
    private Long scheduleTime;

    @ApiModelProperty("驿站ID")
    private Long courierStationId;

    @ApiModelProperty("驿站名称")
    private String courierStationName;

    @ApiModelProperty("服务时长")
    private Integer serviceTimeTotal;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private Integer pageNo = 1;
    @ApiModelProperty("页数")
    private Integer pageSize = 10;
}
