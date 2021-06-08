package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author gaorh
 * @desc 点击量
 * @date 2021/106/08
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "点击量表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportClicks extends BaseEntity {
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("项目ID")
    private Long itemId;
    @ApiModelProperty("项目名称")
    private String itemName;
    @ApiModelProperty("项目类型:1-活动新闻，2-公益课程，3-健身视频")
    private Integer itemType;
    @ApiModelProperty("发布时间")
    private Long releaseTime;
    @ApiModelProperty("备注")
    private String remark;

}
