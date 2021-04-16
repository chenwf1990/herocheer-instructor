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

    @ApiModelProperty("报名结束时间")
    private String signStartTime;

    @ApiModelProperty("报名结束时间")
    private String signEndTime;

    @ApiModelProperty("审批(0.待审核 1.审批通过 2.驳回 3.撤回 4.草稿)")
    private Integer approvalStatus;

    @ApiModelProperty("查询类型(1.待审核 2.已审核 3.我发布的 4.已发布)")
    private Integer queryType;

    @ApiModelProperty("预约状态(1.预约中,2.已关闭)")
    private Integer reservationState;

    @ApiModelProperty("创建人人id")
    private Long createdId;

    @ApiModelProperty("课程状态(0.未开始1.报名中2.报名截止3.上课中4.已结课5.课程取消)")
    private Integer state;

    @ApiModelProperty("页码")
    private int pageNo = 1;

    @ApiModelProperty("页数")
    private int pageSize = 10;


    @ApiModelProperty("授课老师名")
    private String lecturerTeacherName;

    @ApiModelProperty("授课老师ID")
    private Long lecturerTeacherId;

    @ApiModelProperty("发布开始时间")
    private String releaseStartTime;

    @ApiModelProperty("发布结束时间")
    private String releaseEndTime;

}
