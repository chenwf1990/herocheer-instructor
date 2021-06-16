package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc 活动招募-明细
 * @date 2021-01-21 15:55:53
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class ActivityRecruitDetail extends BaseEntity {
    @ApiModelProperty("招募信息主表ID")   
    private Long recruitId;
    @ApiModelProperty("开始服务时间-时分")   
    private String serviceStartTime;
    @ApiModelProperty("结束服务时间-时分")   
    private String serviceEndTime;

    @ApiModelProperty("服务日期")
    private Long serviceDate;
    @ApiModelProperty("招募人数")   
    private Integer recruitNumber;
    @ApiModelProperty("已招募人数")   
    private Integer hadRecruitNumber;

    @ApiModelProperty("开始借用时间-时分")
    private String borrowBeginTime;
    @ApiModelProperty("结束借用时间-时分")
    private String borrowEndTime;

}