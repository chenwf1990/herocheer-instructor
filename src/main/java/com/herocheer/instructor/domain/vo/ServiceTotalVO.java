package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务时长统计汇总VO
 *
 * @author gaorh
 * @create 2021-06-10
 */

@ApiModel(description = "服务时长统计汇总VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTotalVO {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("指导项目")
    private String guideProject;

    @ApiModelProperty("证书等级")
    private String certificateGrade;


    @ApiModelProperty("驿站值班服务次数")
    private Integer dutyServiceCount;

    @ApiModelProperty("服务时长（分钟）")
    private Long dutyServiceTimeTotal;


    @ApiModelProperty("授课次数")
    private Integer courseServiceCount;

    @ApiModelProperty("授课时长（分钟）")
    private Long courseServiceTimeTotal;

    @ApiModelProperty("赛事服务次数")
    private Integer matchServiceCount;

    @ApiModelProperty("赛事服务时长（分钟）")
    private Long matchServiceTimeTotal;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private Integer pageNo = 1;
    @ApiModelProperty("页数")
    private Integer pageSize = 10;
}
