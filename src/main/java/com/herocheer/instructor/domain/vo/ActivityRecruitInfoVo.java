package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-18 14:58
 **/
@Data
public class ActivityRecruitInfoVo extends ActivityRecruitInfo {

    @ApiModelProperty("招募详情)")
    private List<ActivityRecruitDetail> recruitDetails;

    @ApiModelProperty("预约状态(0.已预约 1.取消预约 2.活动下架)")
    private Integer reservationStatus;
}
