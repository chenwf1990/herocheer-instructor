package com.herocheer.instructor.domain.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
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
@ExcelTarget("reportClicks")
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
    @Excel(name = "用户名", orderNum = "1")
    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("项目ID")
    private Long itemId;

    @Excel(name = "项目名称", orderNum = "2")
    @ApiModelProperty("项目名称")
    private String itemName;

    @Excel(name = "项目类型", orderNum = "3")
    @ApiModelProperty("项目类型:1-活动新闻，2-公益课程，3-健身视频")
    private Integer itemType;

    @Excel(name = "发布时间", orderNum = "4")
    @ApiModelProperty("发布时间")
    private Long releaseTime;
    @ApiModelProperty("备注")
    private String remark;

}
