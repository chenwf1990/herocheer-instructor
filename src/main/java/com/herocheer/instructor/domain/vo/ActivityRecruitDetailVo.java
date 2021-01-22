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
    @ApiModelProperty("招募标题")
    private Integer status;
}
