package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc  课程预约表
 * @date 2021-02-22 11:33:19
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class CourseReservation extends BaseEntity {
    @ApiModelProperty("课程id")   
    private Long courseId;
    @ApiModelProperty("用户id")   
    private String userId;
    @ApiModelProperty("姓名")   
    private String name;
    @ApiModelProperty("身份证号")   
    private String identityNumber;
    @ApiModelProperty("手机号")   
    private String phoneNumber;
    @ApiModelProperty("状态 (0.已预约1.取消预约2.已关闭)")   
    private Integer status;

}