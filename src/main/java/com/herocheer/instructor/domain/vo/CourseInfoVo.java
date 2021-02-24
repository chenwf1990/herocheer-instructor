package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.CourseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-02-22 14:51
 **/
@Data
public class CourseInfoVo extends CourseInfo {
    @ApiModelProperty("课程状态(0.未开始1.报名中2.报名截止3.上课中4.已结课)")
    private Integer courseStatus;
}
