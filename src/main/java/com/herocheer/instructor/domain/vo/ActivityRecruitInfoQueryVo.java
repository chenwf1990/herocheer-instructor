package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-18 15:00
 **/
@Data
public class ActivityRecruitInfoQueryVo implements Serializable {
    @ApiModelProperty("招募标题")
    private String title;

    @ApiModelProperty("招募类型(1.驿站2.赛事)")
    private Integer recruitType;

    @ApiModelProperty("招募人(模糊查询)")
    private String createdBy;

    @ApiModelProperty("招募开始日期")
    private Long recruitStartDate;

    @ApiModelProperty("招募结束日期")
    private Long recruitEndDate;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;

    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;

    @ApiModelProperty("状态 (0.待审核1.招募待启动2.撤回3.驳回4.招募中5.招募结束)")
    private Integer status;

    @ApiModelProperty("类型(0.全部1.我发布的2.我审批的)")
    private Integer type;

    @ApiModelProperty("创建人-后台参数")
    private Long userId;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
