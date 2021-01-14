package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.RecruitDetail;
import com.herocheer.instructor.domain.entity.RecruitInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-07 10:53
 **/
@Data
public class RecruitInfoVo extends RecruitInfo {
    @ApiModelProperty("驿站-服务时段")
    private String serviceHours;

    @ApiModelProperty("活动招募时段")
    private List<RecruitDetail> recruitDetails;
}
