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
public class VideoQueryVo implements Serializable {
    @ApiModelProperty("健身主题(支持模糊查询)")
    private String title;
    @ApiModelProperty("内容描述(支持模糊查询)")
    private String content;
    @ApiModelProperty("创建人(支持模糊查询)")
    private String createdBy;
    @ApiModelProperty("创建时间-开始时间")
    private Long beginTime;
    @ApiModelProperty("创建时间-结束时间")
    private Long endTime;
    @ApiModelProperty("状态 0启用 1禁用")
    private Integer state;
    @ApiModelProperty("请求类型 1全部 2关注")
    private Integer reqType;
    @ApiModelProperty("openId")
    private String openId;
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("页数")
    private int pageSize;
}
