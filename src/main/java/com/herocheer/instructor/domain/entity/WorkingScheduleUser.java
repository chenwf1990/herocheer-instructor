package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 排班表人员表
 * @date 2021-01-22 09:55:05
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class WorkingScheduleUser extends BaseEntity {
    @ApiModelProperty("任务编号")   
    private String taskNo;
    @ApiModelProperty("排班id")   
    private Long workingScheduleId;
    @ApiModelProperty("排班用户类型 (1站长 2固定排班人员 3预约)")   
    private Integer type;
    @ApiModelProperty("用户id")   
    private Long userId;
    @ApiModelProperty("用户名称")   
    private String userName;
    @ApiModelProperty("证书等级")   
    private String certificateGrade;
    @ApiModelProperty("指导项目")   
    private String guideProject;
    @ApiModelProperty("签到时间")   
    private Long signInTime;
    @ApiModelProperty("签退时间")   
    private Long signOutTime;
    @ApiModelProperty("服务时长 (单位：分)")   
    private Integer serviceTime;
    @ApiModelProperty("实得时长(单位:分)")   
    private Integer actualServiceTime;
    @ApiModelProperty("审批人")
    private Long approvalId;
    @ApiModelProperty("审批类型 1按照人员签到签退时间进行统计 2按照活动设定时间进行统计 3按实际情况填写时长")   
    private Integer approvalType;
    @ApiModelProperty("审批意见")   
    private String approvalIdea;
    @ApiModelProperty("审批时间")   
    private Long approvalTime;
    @ApiModelProperty("预约状态 0预约 1撤销")   
    private Integer reserveStatus;
    @ApiModelProperty("是否需要审核 0不需要审核 1待审核 2已审核")   
    private Integer status;
    @ApiModelProperty("备注")   
    private String remarks;

}