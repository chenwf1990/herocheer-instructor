package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 取消课表VO
 *
 * @author gaorh
 * @create 2021-06-11
 */

@ApiModel(description = "取消课表VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseScheduleCancelVO {

    @ApiModelProperty("课表id")
    private Long scheduleId;

    @ApiModelProperty("取消原因")
    private String cancelReason;
}
