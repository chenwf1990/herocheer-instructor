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

    @ApiModelProperty("审批状态(0.待审核 1.审核通过 2.驳回 3.撤回 4.草稿)")
    private Integer approvalStatus;

    @ApiModelProperty("状态 (0.招募待启动 1.招募中 2.招募结束 3.已结项 4.活动取消)")
    private Integer status;

    @ApiModelProperty("类型(0.全部 1.我发布的 2.我审批的 3.已发布的 4.招募已结束)")
    private Integer type;

    @ApiModelProperty("创建人-后台参数")
    private Long userId;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
