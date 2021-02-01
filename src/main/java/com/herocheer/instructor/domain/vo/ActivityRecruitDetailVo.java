package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-22 10:16
 **/
@Data
public class ActivityRecruitDetailVo extends ActivityRecruitDetail {
    @ApiModelProperty("状态(0.可预约1.不可预约)")
    private Integer status;
}
