package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.CourseInfo;
import com.herocheer.instructor.domain.entity.CourseSchedule;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-02-22 14:51
 **/
@Data
public class CourseInfoVo extends CourseInfo {
    @ApiModelProperty("预约Id")
    private Long reservationId;

    @ApiModelProperty("预约状态(0.已预约1.取消预约2.已关闭)")
    private Integer reservationStatus;

    @ApiModelProperty("签到状态:0-未签到，1-已签到，2-签到失败")
    private Integer signStatus;

    @ApiModelProperty("签到时间")
    private Long signTime;

    @ApiModelProperty("课表信息")
    private List<CourseSchedule> CourseScheduleList;

    @ApiModelProperty("预约信息")
    private ReservationListVO Reservation;

    @ApiModelProperty("指导项目")
    private String guideProject;

}
