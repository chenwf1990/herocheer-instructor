package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenwf
 * @desc 驿站列表查询请求参数
 * @date 2021/1/4
 * @company 厦门熙重电子科技有限公司
 */
@NoArgsConstructor
@Data
public class BannerQueryVo implements Serializable {
    @ApiModelProperty("banner名称(支持模糊查询)")
    private String name;
    @ApiModelProperty("banner位置 1首页")
    private Integer position;
    @ApiModelProperty("是否上下架 0上架 1下架")
    private Integer isPublic;
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("页数")
    private int pageSize;
}
