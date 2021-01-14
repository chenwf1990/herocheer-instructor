package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/11
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel("值班人员信息实体")
@Data
public class WorkingSchedulsUserVo extends WorkingScheduleUser{
    @ApiModelProperty("驿站id")
    private Long courierStationId;
    @ApiModelProperty("驿站名称")
    private String courierStationName;
    @ApiModelProperty("排班日期")
    private Long scheduleTime;
    @ApiModelProperty("服务开始时段 (时分)")
    private String serviceBeginTime;
    @ApiModelProperty("服务结束时段 (时分)")
    private String serviceEndTime;
    @ApiModelProperty("指导项目")
    private String guideProject;
    @ApiModelProperty("证书等级")
    private String certificateGrade;
}
