package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.CourseSchedule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * VO
 *
 * @author gaorh
 * @create 2021-05-17
 */

@ApiModel(description = "课表信息VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CourseScheduleInfoVO extends CourseSchedule {

    @ApiModelProperty("课表状态")
    private String courseScheduleStatus;
}
