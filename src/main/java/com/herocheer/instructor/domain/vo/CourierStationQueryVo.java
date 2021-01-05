package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenwf
 * @desc 指导员列表查询请求参数
 * @date 2021/1/4
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class CourierStationQueryVo implements Serializable {
    @ApiModelProperty("常驻区域")
    private Long areaCode;
    @ApiModelProperty("证书等级")
    private int grade;
    @ApiModelProperty("指导项目(支持前后模糊查询)")
    private String guideProject;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
}
