package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 招募预约信息VO
 * @author: Linjf
 * @create date: 2021-01-28 16:18
 **/
@Data
public class ReservationInfoVo implements Serializable {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("活动id")
    private Long activityId;

    @ApiModelProperty("服务日期")
    private Long scheduleTime;

    @ApiModelProperty("服务开始时段 (时分)")
    private String serviceBeginTime;

    @ApiModelProperty("服务结束时段 (时分)")
    private String serviceEndTime;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("证书等级")
    private String certificateGrade;

    @ApiModelProperty("指导项目")
    private String guideProject;

    @ApiModelProperty("创建时间即预约时间")
    private Long createdTime;

    @ApiModelProperty("预约状态 0预约 1撤销")
    private Integer reserveStatus;

    @ApiModelProperty("签到时间")
    private Long signInTime;

    @ApiModelProperty("签退时间")
    private Long signOutTime;

    @ApiModelProperty("服务时长 (单位：分)")
    private Integer serviceTime;
}
