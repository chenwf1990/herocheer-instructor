package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 招募审批日志 
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class RecruitApprovalLog extends BaseEntity {
    @ApiModelProperty("招募信息主表Id")   
    private Long recruitId;
    @ApiModelProperty("审批状态 (1.通过2.驳回)")   
    private Integer approvalStatus;
    @ApiModelProperty("审批意见")   
    private String approvalComments;

}