package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-06 14:29
 **/
@Data
public class ServiceHoursQueryVo implements Serializable {

    @ApiModelProperty("驿站名称")
    private String stationName;

    @ApiModelProperty("驿站Id")
    private Long stationId;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;

    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;

    @ApiModelProperty("姓名(支持前后模糊查询)")
    private String userName;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
