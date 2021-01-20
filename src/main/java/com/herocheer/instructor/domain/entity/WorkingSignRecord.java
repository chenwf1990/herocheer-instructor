package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 值班签到记录
 * @date 2021-01-19 17:57:22
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class WorkingSignRecord extends BaseEntity {
    @ApiModelProperty("值班人员id")   
    private Long workingScheduleUserId;
    @ApiModelProperty("打卡类型 1签到2签退")   
    private Integer type;
    @ApiModelProperty("是否补卡 0否 1是")   
    private Integer isReissueCard;
    @ApiModelProperty("打卡状态 0正常 1异常")   
    private Integer signStatus;
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