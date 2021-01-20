package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 活动记录表
 * @date 2021-01-20 09:50:03
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class ActivityRecord extends BaseEntity {
    @ApiModelProperty("招募信息表ID")   
    private Long recruitId;
    @ApiModelProperty("招募信息详情ID")   
    private Long recruitDetailId;
    @ApiModelProperty("关联招募主题")   
    private String recruitTitle;
    @ApiModelProperty("任务编号")   
    private String taskNo;
    @ApiModelProperty("服务开始时间")   
    private Long serviceStartTime;
    @ApiModelProperty("服务结束时间")   
    private Long serviceEndTime;
    @ApiModelProperty("指导员ID")   
    private Long instructorId;
    @ApiModelProperty("证书等级")   
    private Integer certificateLevel;
    @ApiModelProperty("指导员姓名")   
    private String name;
    @ApiModelProperty("指导项目")   
    private String instructorItem;
    @ApiModelProperty("预约状态 (0.预约成功1.预约取消)")   
    private Integer reserveStatus;
    @ApiModelProperty("打卡状态 (0.正常1.异常)")   
    private Integer signStatus;
    @ApiModelProperty("状态 (0.待核查1.已核查)")   
    private Integer status;
    @ApiModelProperty("服务时长")   
    private String serviceDuration;
    @ApiModelProperty("签到时间")   
    private Long signInTime;
    @ApiModelProperty("签退时间")   
    private Long signOutTime;
    @ApiModelProperty("超出服务时长")   
    private String exceedServiceDuration;
    @ApiModelProperty("审批类型 (0.按服务时长)")   
    private Integer approvalType;
    @ApiModelProperty("实得时长")   
    private String actualServiceDuration;
    @ApiModelProperty("审批意见")   
    private String approvalComments;
    @ApiModelProperty("审批人")   
    private String approvalBy;
    @ApiModelProperty("审批时间")   
    private Long approvalTime;
    @ApiModelProperty("审批人Id")   
    private Long approvalId;

}