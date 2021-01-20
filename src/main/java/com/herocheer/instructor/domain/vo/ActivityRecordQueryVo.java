package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description: 活动记录查询vo
 * @author: Linjf
 * @create date: 2021-01-18 14:56
 **/
@Data
public class ActivityRecordQueryVo {

    @ApiModelProperty("任务编号")
    private Integer taskNumber;

    @ApiModelProperty("关联活动招募主题")
    private String recruitTitle;

    @ApiModelProperty("指导员姓名")
    private String name;

    @ApiModelProperty("指导项目")
    private String instructorItem;

    @ApiModelProperty("打卡状态 (0.正常1.异常)")
    private Integer signStatus;

    @ApiModelProperty("状态 (0.无需核查1.待核查2.已核查)")
    private Integer status;

    @ApiModelProperty("服务开始时间")
    private Long serviceStartTime;

    @ApiModelProperty("服务结束时间")
    private Long serviceEndTime;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
