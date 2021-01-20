package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Linjf
 * @desc 活动补卡
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class ActivityReplaceCard extends BaseEntity {
    @ApiModelProperty("关联记录id")   
    private Long recordId;
    @ApiModelProperty("补卡时间")   
    private Long replaceCardTime;
    @ApiModelProperty("补卡说明")   
    private String replaceCardReason;
    @ApiModelProperty("审批类型 (0.待审核1.通过2.驳回)")   
    private Integer approvalStatus;
    @ApiModelProperty("审批意见")   
    private String approvalComments;
    @ApiModelProperty("审批人Id")   
    private Long approvalId;
    @ApiModelProperty("审批人")   
    private String approvalBy;
    @ApiModelProperty("审批时间")   
    private Long approvalTime;

}