package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 预约成员VO
 *
 * @author gaorh
 * @create 2021-05-17
 */

@ApiModel(description = "预约成员VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationMemberVO {
    @ApiModelProperty("报名人员")
    List<ReservationVO> reservationList;

    @ApiModelProperty("课表ID")
    private Long courseScheduleId;

    @ApiModelProperty("课表的预约日期（固定课程）")
    private Long courseDate;
}
