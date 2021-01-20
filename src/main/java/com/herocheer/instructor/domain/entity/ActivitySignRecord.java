package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 活动打卡记录
 * @date 2021-01-20 09:47:33
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class ActivitySignRecord extends BaseEntity {
    @ApiModelProperty("活动记录id")   
    private Long activityRecordId;
    @ApiModelProperty("打卡类型 (1.签到打卡2.签退打卡)")   
    private Integer signType;
    @ApiModelProperty("是否补卡 (0.否,1.是)")   
    private Integer isReplaceCard;
    @ApiModelProperty("打卡状态(0.正常1.异常)")   
    private Integer signStatus;
    @ApiModelProperty("打卡时间")   
    private Long signTime;
    @ApiModelProperty("打卡地址")   
    private String signAddress;
    @ApiModelProperty("经度")   
    private String longitude;
    @ApiModelProperty("纬度")   
    private String latitude;
    @ApiModelProperty("活动照片")   
    private String activityImage;

}