package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 招募信息查询Vo
 * @author: Linjf
 * @create date: 2021-01-06 17:54
 **/
@Data
public class RecruitInfoQueryVo implements Serializable {
    @ApiModelProperty("招募标题")
    private String title;

    @ApiModelProperty("招募类型(1.驿站2.赛事)")
    private Integer recruitType;

    @ApiModelProperty("招募人(模糊查询)")
    private String createdBy;

    @ApiModelProperty("招募开始日期")
    private String startRecruitDate;

    @ApiModelProperty("招募结束日期")
    private String endRecruitDate;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;

    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;

    @ApiModelProperty("状态 (1.撤回2.驳回3.待审核4.招募待启动5.招募中6.招募结束)")
    private Integer status;

    @ApiModelProperty("类型(1.未发布 2.已发布)")
    private Integer type;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
