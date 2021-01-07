package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 新闻公告log
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class NewsNoticeLog extends BaseEntity {
    @ApiModelProperty("新闻id")   
    private Integer newsNoticeId;
    @ApiModelProperty("审核状态 0待审核1驳回2通过3撤回4已发布")   
    private Integer auditState;
    @ApiModelProperty("审核意见")   
    private String auditIdea;
    @ApiModelProperty("备注")   
    private String remarks;

}