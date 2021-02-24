package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-02-22 18:52
 **/
@Data
public class CourseReservationVo  implements Serializable{
    @ApiModelProperty("课程id")
    private Long courseId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("身份证号")
    private String identityNumber;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("验证码")
    private String verificationCode;
}
