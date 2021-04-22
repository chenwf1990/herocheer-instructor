package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author chenwf
 * @desc 预约记录
 * @date 2021-02-24 16:53:46
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "预约记录表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Reservation extends BaseEntity {
    @ApiModelProperty("关联预约主表id")   
    private Long relevanceId;
    @ApiModelProperty("类型(1.驿站招募2.赛事招募3.课程培训)")   
    private Integer type;
    @ApiModelProperty("来源(1.线上 2.线下)")
    private Integer source;
    @ApiModelProperty("关联任务id")   
    private Long workingId;
    @ApiModelProperty("标题")   
    private String title;
    @ApiModelProperty("照片")   
    private String image;
    @ApiModelProperty("开始时间")   
    private Long startTime;
    @ApiModelProperty("结束时间")   
    private Long endTime;
    @ApiModelProperty("地址")   
    private String address;
    @ApiModelProperty("经度")   
    private String longitude;
    @ApiModelProperty("纬度")   
    private String latitude;
    @ApiModelProperty("用户id")   
    private Long userId;
    @ApiModelProperty("姓名")   
    private String name;
    @ApiModelProperty("身份证号")   
    private String identityNumber;
    @ApiModelProperty("手机号")   
    private String phone;
    @ApiModelProperty("状态 (0.已预约 1.取消预约 2.活动撤销)")
    private Integer status;

    @ApiModelProperty("签到时间")
    private Long signTime;
    @ApiModelProperty("签到类型：0-线下签到，1-线上签到")
    private Integer signType;
    @ApiModelProperty("签到状态：0-未签到，1-已签到，2-签到失败")
    private Integer signStatus;
}