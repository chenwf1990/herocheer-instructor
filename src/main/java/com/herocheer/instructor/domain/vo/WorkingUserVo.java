package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.WorkingReplaceCard;
import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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
    @ApiModelProperty("值班人员id")
    private Long workingScheduleUserId;
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
    @ApiModelProperty("任务状态 0.正常 1.异常 2.待完成")
    private Integer signStatus;
    @ApiModelProperty("打卡记录")
    private List<WorkingSignRecord> signRecords;
    @ApiModelProperty("补卡记录")
    private List<WorkingReplaceCard> replaceCards;
    @ApiModelProperty("招募活动类型 1驿站 2赛事")
    private Integer activityType;
    @ApiModelProperty("活动id")
    private Long activityId;
    @ApiModelProperty("活动标题")
    private String activityTitle;
    @ApiModelProperty("签到状态 0.正常签到 1.异常签到")
    private Integer signInStatus;
    @ApiModelProperty("签退状态 0.正常签到 1.异常签到")
    private Integer signOutStatus;
    @ApiModelProperty("审核状态 0不需要审核 1待审核 2审核通过")
    private Integer status;
    @ApiModelProperty("签到范围")
    private Integer signScope;
}
