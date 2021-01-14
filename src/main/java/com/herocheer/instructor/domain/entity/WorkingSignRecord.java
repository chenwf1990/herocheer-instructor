package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 值班签到记录
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class WorkingSignRecord extends BaseEntity {
    @ApiModelProperty("值班人员id")   
    private Long workingScheduleUserId;
    @ApiModelProperty("打卡类型 1签到2签退")   
    private Integer type;
    @ApiModelProperty("打卡时间")   
    private Long signTime;
    @ApiModelProperty("打卡位置")   
    private String signPlace;
    @ApiModelProperty("经度")   
    private String longitude;
    @ApiModelProperty("纬度")   
    private String latitude;
    @ApiModelProperty("图片 (多个逗号隔开)")   
    private String pics;
    @ApiModelProperty("备注")   
    private String remarks;

}