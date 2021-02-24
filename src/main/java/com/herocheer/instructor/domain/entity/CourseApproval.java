package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc  课程审批表
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class CourseApproval extends BaseEntity {
    @ApiModelProperty("课程id")   
    private Long courseId;
    @ApiModelProperty("审批状态(1.通过2.驳回)")
    private Integer approvalStatus;
    @ApiModelProperty("审批意见")   
    private String approvalComments;

}