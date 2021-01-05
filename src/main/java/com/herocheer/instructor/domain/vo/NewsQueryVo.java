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
public class NewsQueryVo implements Serializable {
    @ApiModelProperty("标题(支持模糊查询)")
    private String title;
    @ApiModelProperty("类型1新闻 2活动")
    private int type;
    @ApiModelProperty("0待审核1驳回2通过3撤回4已发布")
    private int auditState;
    @ApiModelProperty("创建人(支持模糊查询)")
    private String createdBy;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
}
