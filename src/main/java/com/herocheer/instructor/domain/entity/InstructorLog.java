package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 指导员审批日志
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class InstructorLog extends BaseEntity {
    @ApiModelProperty("指导员id")   
    private Long instructorId;
    @ApiModelProperty("审核状态 0待审核1审核通过2审核驳回")   
    private Integer auditState;
    @ApiModelProperty("审核意见")   
    private String auditIdea;
    @ApiModelProperty("备注")   
    private String remarks;

}