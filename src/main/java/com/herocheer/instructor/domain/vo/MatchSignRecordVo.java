package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 赛事打卡记录
 * @author: Linjf
 * @create date: 2021-01-29 11:18
 **/
@Data
public class MatchSignRecordVo  implements Serializable {
    @ApiModelProperty("主键")
    private Long Id;

    @ApiModelProperty("活动id")
    private Long activityId;

    @ApiModelProperty("名称")
    private String userName;

    @ApiModelProperty("打卡时间")
    private Long signTime;

    @ApiModelProperty("打卡位置")
    private String signPlace;

    @ApiModelProperty("图片 (多个逗号隔开)")
    private String pics;
}
