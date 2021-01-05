package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 新闻公告
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class NewsNotice extends BaseEntity {
    @ApiModelProperty("类型 1新闻2活动")   
    private Integer type;
    @ApiModelProperty("新闻图片")   
    private String pic;
    @ApiModelProperty("标题")   
    private String title;
    @ApiModelProperty("发布时间")   
    private Long pubilcTime;
    @ApiModelProperty("审核状态 0待审核1驳回2通过3撤回4已发布")   
    private Integer auditState;
    @ApiModelProperty("内容")   
    private String content;
    @ApiModelProperty("备注")   
    private String remarks;

}