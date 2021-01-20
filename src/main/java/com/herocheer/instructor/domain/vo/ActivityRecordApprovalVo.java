package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-19 09:16
 **/
@Data
public class ActivityRecordApprovalVo implements Serializable {
    @ApiModelProperty("活动记录id")
    private Long id;

    @ApiModelProperty("审批类型 (0.按照人员签到签退时间进行统计,1.按照活动设定时间进行统计,2.按实际情况填写时长)")
    private Integer approvalType;

    @ApiModelProperty("实得时长")
    private String actualServiceDuration;

    @ApiModelProperty("审批意见")
    private String approvalComments;
}
