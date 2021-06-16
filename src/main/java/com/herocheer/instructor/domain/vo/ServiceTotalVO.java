package com.herocheer.instructor.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
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
@ExcelTarget("serviceTotal")
@ApiModel(description = "服务时长统计汇总VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTotalVO {

    @Excel(name = "姓名", orderNum = "1")
    @ApiModelProperty("姓名")
    private String name;

    @Excel(name = "指导项目", orderNum = "2")
    @ApiModelProperty("指导项目")
    private String guideProject;

    @Excel(name = "证书等级", orderNum = "3")
    @ApiModelProperty("证书等级")
    private String certificateGrade;

    @Excel(name = "驿站值班服务次数", orderNum = "4",width = 20)
    @ApiModelProperty("驿站值班服务次数")
    private Integer dutyServiceCount;

    @Excel(name = "服务时长（分钟）", orderNum = "5",width = 20)
    @ApiModelProperty("服务时长（分钟）")
    private Long dutyServiceTimeTotal;

    @Excel(name = "授课次数", orderNum = "6",width = 20)
    @ApiModelProperty("授课次数")
    private Integer courseServiceCount;

    @Excel(name = "授课时长（分钟）", orderNum = "7",width = 20)
    @ApiModelProperty("授课时长（分钟）")
    private Long courseServiceTimeTotal;

    @Excel(name = "赛事服务次数", orderNum = "8",width = 20)
    @ApiModelProperty("赛事服务次数")
    private Integer matchServiceCount;

    @Excel(name = "赛事服务时长（分钟）", orderNum = "9",width = 20)
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
