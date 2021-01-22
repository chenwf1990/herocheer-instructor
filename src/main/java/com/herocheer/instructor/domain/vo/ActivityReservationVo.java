package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 活动预约入参
 * @author: Linjf
 * @create date: 2021-01-21 09:01
 **/
@Data
public class ActivityReservationVo implements Serializable {
    @ApiModelProperty("预约日期")
    private Long reservationDate;
    @ApiModelProperty("预约时段ID")
    private String recruitDetailIds;
}
