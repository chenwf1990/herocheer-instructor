package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.CourseSchedule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 课表VO
 *
 * @author gaorh
 * @create 2021-05-14
 */
@ApiModel(description = "课表信息VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseScheduleVO{

    @ApiModelProperty("课表信息")
    private List<CourseSchedule> CourseScheduleList;
}
