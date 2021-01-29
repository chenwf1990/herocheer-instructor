package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 预约记录查询VO
 * @author: Linjf
 * @create date: 2021-01-28 18:51
 **/
@Data
public class ReservationInfoQueryVo implements Serializable {

    @ApiModelProperty("1.预约信息2.服务信息")
    private Integer type;

    @ApiModelProperty("活动id")
    private Long activityId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("服务日期")
    private Long scheduleTime;

    @ApiModelProperty("预约状态 0预约 1撤销")
    private Integer reserveStatus;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
