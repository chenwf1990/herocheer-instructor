package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc 服务时长统计vo
 * @date 2021/1/28
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel("服务时长统计模型")
@Data
public class ServiceHoursReportVo {
    @ApiModelProperty("姓名")
    private String userName;
    @ApiModelProperty("服务次数")
    private Integer serviceCount;
    @ApiModelProperty("服务时长：单位(分)")
    private Integer serviceTime;
}
