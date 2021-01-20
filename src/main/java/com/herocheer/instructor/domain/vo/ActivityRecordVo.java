package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.ActivityRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-18 17:27
 **/
@Data
public class ActivityRecordVo extends ActivityRecord {
    @ApiModelProperty("签到时间")
    private Long signStatusTime;
    @ApiModelProperty("签退时间")
    private Long signEndTime;
}
