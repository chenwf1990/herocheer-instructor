package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 移动端 审批列表返回Vo
 * @author: Linjf
 * @create date: 2021-01-26 10:50
 **/
@Data
public class ApplicationListVo implements Serializable {
    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("数据来源:0.招募1.新闻")
    private Integer source;
    @ApiModelProperty("类型:当source=0时(1.驿站招募2.赛事招募);当source=1(1.新闻2.活动3.公告)")
    private Integer type;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("招募开始时间")
    private Long recruitStartDate;
    @ApiModelProperty("招募结束时间")
    private Long recruitEndDate;
    @ApiModelProperty("创建时间即申请时间")
    private Long createdTime;
    @ApiModelProperty("状态:0.待审核1.审核通过2.驳回")
    private Integer status;
}
