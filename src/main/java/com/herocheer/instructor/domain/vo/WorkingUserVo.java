package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc 用户值班信息
 * @date 2021/1/20
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class WorkingUserVo {
    @ApiModelProperty("值班任务id")
    private Long id;
    @ApiModelProperty("值班日期")
    private Long scheduleTime;
    @ApiModelProperty("值班日期文本")
    private String scheduleTimeText;
    @ApiModelProperty("服务开始时段")
    private String serviceBeginTime;
    @ApiModelProperty("服务结束时段")
    private String serviceEndTime;
    @ApiModelProperty("驿站id")
    private Long courierStationId;
    @ApiModelProperty("驿站名称")
    private String courierStationName;
    @ApiModelProperty("驿站地址")
    private String address;
    @ApiModelProperty("经度")
    private String longitude;
    @ApiModelProperty("纬度")
    private String latitude;
    @ApiModelProperty("签到时间")
    private Long signInTime;
    @ApiModelProperty("签退时间")
    private Long signOutTime;
    @ApiModelProperty("服务时长 (单位：分)")
    private Integer serviceTime;
    @ApiModelProperty("签到状态 0.正常签到 1.异常签到 2.待完成")
    private Integer signStatus;
}
