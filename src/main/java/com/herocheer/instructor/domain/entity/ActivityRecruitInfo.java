package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc 招募信息主表
 * @date 2021-02-25 09:30:41
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class ActivityRecruitInfo extends BaseEntity {
    @ApiModelProperty("招募类型 (1.驿站招募2.活动招募)")   
    private Integer recruitType;
    @ApiModelProperty("标题")   
    private String title;
    @ApiModelProperty("招募内容")   
    private String content;
    @ApiModelProperty("招募照片")   
    private String image;
    @ApiModelProperty("地址")   
    private String address;
    @ApiModelProperty("经度")   
    private String longitude;
    @ApiModelProperty("纬度")   
    private String latitude;
    @ApiModelProperty("赛事-时长审批人ID")   
    private Long matchApproverId;
    @ApiModelProperty("赛事-时长审批人")   
    private String matchApprover;
    @ApiModelProperty("驿站-所属驿站ID")   
    private Long courierStationId;
    @ApiModelProperty("驿站-所属驿站")   
    private String courierStation;
    @ApiModelProperty("驿站-服务开始日期")   
    private Long serviceStartDate;
    @ApiModelProperty("驿站-服务结束日期")   
    private Long serviceEndDate;
    @ApiModelProperty("驿站-服务时段")   
    private String serviceHours;
    @ApiModelProperty("驿站-招募人数")   
    private Integer recruitNumber;
    @ApiModelProperty("状态 (0.招募待启动 1.招募中 2.招募结束 3.已结项 4.活动取消)")
    private Integer status;
    @ApiModelProperty("审批状态(0.待审核 1.审核通过 2.驳回 3.撤回 4.草稿)")
    private Integer approvalStatus;
    @ApiModelProperty("是否在banner显示(0.否1.是)")   
    private Integer showBanner;
    @ApiModelProperty("是否置顶(0.否1.是)")   
    private Integer placedTop;
    @ApiModelProperty("开始招募日期")   
    private Long recruitStartDate;
    @ApiModelProperty("结束招募日期")   
    private Long recruitEndDate;
    @ApiModelProperty("审批意见")   
    private String approvalComments;
    @ApiModelProperty("部门名称")   
    private String deptName;
    @ApiModelProperty("部门id")   
    private Long deptId;
    @ApiModelProperty("审批人名称")
    private String approvalBy;
    @ApiModelProperty("审批时间")
    private Long approvalTime;
    @ApiModelProperty("审批人id")
    private Long approvalId;

    @ApiModelProperty("器材借用时段")
    private String borrowHours;
}