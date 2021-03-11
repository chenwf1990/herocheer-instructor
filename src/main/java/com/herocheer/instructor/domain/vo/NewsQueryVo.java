package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
    @ApiModelProperty("类型1新闻 2活动 3公告")
    private Integer type;
    @ApiModelProperty("审核状态(集合) 0待审核1通过2驳回3撤回")
    private List<Integer> auditStates;
    @ApiModelProperty("创建人(支持模糊查询)")
    private String createdBy;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("页数")
    private int pageSize;
}
