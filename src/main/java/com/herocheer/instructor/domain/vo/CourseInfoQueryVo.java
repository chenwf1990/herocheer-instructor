package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 课程查询Vo
 * @author: Linjf
 * @create date: 2021-02-22 11:34
 **/
@Data
public class CourseInfoQueryVo implements Serializable {
    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("课程类型 (1.实践指导动作2.理论课)")
    private Integer type;

    @ApiModelProperty("创建人名称")
    private String createdBy;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("审批开始时间")
    private String approvalStartTime;

    @ApiModelProperty("审批结束时间")
    private String approvalEndTime;

    @ApiModelProperty("状态 (0.待审核1.审批通过2.撤回3.驳回)")
    private Integer state;

    @ApiModelProperty("查询类型(1.待审核 2.已审核 3.我发布的)")
    private Integer queryType;

    @ApiModelProperty("预约状态(1.预约中,2.已关闭)")
    private Integer reservationState;

    @ApiModelProperty("创建人人id")
    private Long createdId;

    @ApiModelProperty("课程状态(0.未开始1.报名中2.报名截止3.上课中4.已结课)")
    private Integer courseState;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
