package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域VO
 *
 * @author gaorh
 * @create 2021-03-01
 */

@ApiModel(description = "区域VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaVO {
    private Long id;
    @ApiModelProperty("父ID")
    private Long pid;
    @ApiModelProperty("区域名称")
    private String areaName;
    @ApiModelProperty("省-市-区-街道-社区")
    private String areaCode;
    @ApiModelProperty("行政编码——350000000000：福建省")
    private String chinaCode;
    @ApiModelProperty("级别—— 0：国家、1：省、2：市、3：区 4：街道 5：社区")
    private Integer level;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("更新者ID")
    private Long updateId;
    @ApiModelProperty("更新者")
    private String updateBy;
    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status = true;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;
}
