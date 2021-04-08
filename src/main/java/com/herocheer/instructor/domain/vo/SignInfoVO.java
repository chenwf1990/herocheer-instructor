package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 签到信息VO
 *
 * @author gaorh
 * @create 2021-04-01
 */

@ApiModel(description = "签到信息VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInfoVO {
    @ApiModelProperty("查询类型(0.全部预约记录1.招募或课程的预约记录3.个人的预约记录)")
    private Integer queryType;

    @ApiModelProperty("预约类型(1:驿站招募,2:赛事招募、3:课程培训)")
    @NotBlank(message = "预约类型")
    private Integer type = 3;
    @ApiModelProperty("招募或课程id")
    @NotBlank(message = "招募或课程id")
    private Long relevanceId;

    @ApiModelProperty("签到状态：0-未签到，1-已签到，2-签到失败")
    private Integer signStatus;
    @ApiModelProperty("签到类型：0-线下签到，1-线上签到，2-全部")
    private Integer signType;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;

    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;
}
