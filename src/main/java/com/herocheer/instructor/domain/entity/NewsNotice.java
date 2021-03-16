package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 新闻公告
 * @date 2021-03-12 10:47:47
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class NewsNotice extends BaseEntity {
    @ApiModelProperty("类型 1新闻2活动3公告")
    private Integer type;
    @ApiModelProperty("新闻图片")   
    private String pic;
    @ApiModelProperty("标题")   
    private String title;
    @ApiModelProperty("审核状态 0待审核1通过2驳回3撤回4草稿")
    private Integer auditState;
    @ApiModelProperty("审核时间")   
    private Long auditTime;
    @ApiModelProperty("审核意见")   
    private String auditIdea;
    @ApiModelProperty("内容")   
    private String content;
    @ApiModelProperty("置顶 0否 1是")   
    private Integer top;
    @ApiModelProperty("部门名称")   
    private String deptName;
    @ApiModelProperty("部门id")   
    private Long deptId;
    @ApiModelProperty("备注")   
    private String remarks;
    @ApiModelProperty("是否上架(0.上架1.下架)")
    private Integer isPublic;

}