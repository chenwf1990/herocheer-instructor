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
    private String areaCode;
    @ApiModelProperty("驿站等级")
    private String gradeName;
    @ApiModelProperty("驿站名称(支持前后模糊查询)")
    private String name;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("请求类型 1驿站 2我关注的驿站")
    private Integer reqType;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("状态 0启用 1禁用")
    private Integer state;
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("页数")
    private int pageSize;
}
