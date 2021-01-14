package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 活动招募-明细 
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class RecruitDetail extends BaseEntity {
    @ApiModelProperty("招募信息主表ID")   
    private Long recruitId;
    @ApiModelProperty("开始服务时间")   
    private String startServiceTime;
    @ApiModelProperty("结束服务时间")   
    private String endServiceTime;
    @ApiModelProperty("招募人数")   
    private Integer recruitNumber;

}