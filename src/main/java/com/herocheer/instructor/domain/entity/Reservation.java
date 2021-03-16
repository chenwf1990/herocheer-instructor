package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 预约记录
 * @date 2021-02-24 16:53:46
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class Reservation extends BaseEntity {
    @ApiModelProperty("关联预约主表id")   
    private Long relevanceId;
    @ApiModelProperty("类型(1.驿站招募2.赛事招募3.课程培训)")   
    private Integer type;
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
    @ApiModelProperty("状态 (0.已预约 1.取消预约 2.活动撤销 3.已结束 4.已关闭)")
    private Integer status;

}