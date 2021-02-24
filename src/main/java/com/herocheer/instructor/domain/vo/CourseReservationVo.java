package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.CourseReservation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-02-22 18:52
 **/
@Data
public class CourseReservationVo  extends CourseReservation {
    @ApiModelProperty("验证码")
    private String verificationCode;
}
