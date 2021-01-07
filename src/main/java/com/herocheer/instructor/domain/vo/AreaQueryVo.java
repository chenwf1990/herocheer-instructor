package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenwf
 * @desc 驿站列表查询请求参数
 * @date 2021/1/4
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class AreaQueryVo implements Serializable {
    @ApiModelProperty("区域名称(支持模糊查询)")
    private String areaName;
    @ApiModelProperty("区域类型|级别(0：国家、1：省、2：市、3：区 4：街道 5：社区)")
    private int level;
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("页数")
    private int pageSize;
}
