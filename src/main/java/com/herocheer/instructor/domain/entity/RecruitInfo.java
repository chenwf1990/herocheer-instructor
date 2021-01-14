package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 招募信息主表 
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class RecruitInfo extends BaseEntity {
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
    @ApiModelProperty("赛事-时长审批人ID")   
    private Long ApproverId;
    @ApiModelProperty("赛事-时长审批人")   
    private String Approver;
    @ApiModelProperty("驿站-所属驿站ID")   
    private Long courierStationId;
    @ApiModelProperty("驿站-所属驿站")   
    private String courierStation;
    @ApiModelProperty("驿站-服务开始日期")   
    private String startServiceDate;
    @ApiModelProperty("驿站-服务结束日期")   
    private String endServiceDate;
    @ApiModelProperty("驿站-服务时段ID")   
    private Long serviceHoursId;
    @ApiModelProperty("驿站-招募人数")   
    private Integer recruitNumber;
    @ApiModelProperty("状态 (1.撤回2.驳回3.待审核4.招募待启动5.招募中6.招募结束)")   
    private Integer status;
    @ApiModelProperty("审批状态 (1.通过2.驳回)")   
    private Integer approvalStatus;
    @ApiModelProperty("开始招募日期")   
    private String startRecruitDate;
    @ApiModelProperty("结束招募日期")   
    private String endRecruitDate;
    @ApiModelProperty("审批意见")   
    private String approvalComments;
}