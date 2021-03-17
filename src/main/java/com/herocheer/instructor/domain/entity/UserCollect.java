package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 用户收藏
 * @date 2021-02-25 09:20:29
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class UserCollect extends BaseEntity {
    @ApiModelProperty("关联记录id")   
    private Long objectId;
    @ApiModelProperty("关联记录名称")   
    private String objectName;
    @ApiModelProperty("收藏类型 1驿站 2健身视频")
    private Integer type;
    @ApiModelProperty("用户id（暂时没用）")   
    private Long userId;
    @ApiModelProperty("微信openId")   
    private String openId;
    @ApiModelProperty("审批人")   
    private String remarks;

}