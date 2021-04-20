package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 预约查询Vo
 * @author: Linjf
 * @create date: 2021-02-24 19:04
 **/
@Data
public class ReservationQueryVo implements Serializable {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("身份证号")
    private String identityNumber;

    @ApiModelProperty("查询类型(0.全部预约记录1.招募或课程的预约记录3.个人的预约记录)")
    private Integer queryType;

    @ApiModelProperty("预约类型(1.驿站招募2.赛事招募3.课程培训)")
    private Integer type;

    @ApiModelProperty("预约状态(0.预约成功 1.取消预约 2.活动取消)")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

    @ApiModelProperty("招募或课程id")
    private Long relevanceId;

    @ApiModelProperty("标题(模糊查询)")
    private String title;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;

    @ApiModelProperty("携带关系：0-本人，1-儿子，2-女儿")
    private Integer relationType = 0;
}
