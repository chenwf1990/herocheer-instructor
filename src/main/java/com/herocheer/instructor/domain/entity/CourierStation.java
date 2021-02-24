package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 驿站
 * @date 2021-02-24 20:41:10
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class CourierStation extends BaseEntity {
    @ApiModelProperty("区域编码")   
    private String areaCode;
    @ApiModelProperty("区域名称")   
    private String areaName;
    @ApiModelProperty("驿站名称")   
    private String name;
    @ApiModelProperty("驿站地址")   
    private String address;
    @ApiModelProperty("经度")   
    private String longitude;
    @ApiModelProperty("纬度")   
    private String latitude;
    @ApiModelProperty("驿站等级名称")   
    private String gradeName;
    @ApiModelProperty("规模大小")   
    private String scale;
    @ApiModelProperty("负责人id")   
    private Long userId;
    @ApiModelProperty("负责人名称")   
    private String userName;
    @ApiModelProperty("驿站照片 (最多3张，多个逗号隔开)")   
    private String pics;
    @ApiModelProperty("驿站开放开始时间段（时分）")   
    private String openBeginTime;
    @ApiModelProperty("驿站开放结束时间段（时分）")   
    private String openEndTime;
    @ApiModelProperty("打卡范围")   
    private String signScope;
    @ApiModelProperty("状态 0启用 1禁用")   
    private Integer state;
    @ApiModelProperty("备注")   
    private String remarks;

}