package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 系统字典VO
 *
 * @author gaorh
 * @create 2021-01-20
 */
@ApiModel(description = "系统字典VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictVO {
    private Long id;
    @ApiModelProperty("父ID")
    private String pid;
    @NotBlank(message = "字典名称不能为空")
    @ApiModelProperty("字典名称")
    private String dictName;
    @ApiModelProperty("字典编码使用汉字拼音，如：订单状态——DTZT")
    private String dictCode;
    @ApiModelProperty("排序")
    private String sortNo;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("更新者ID")
    private Long updateId;
    @ApiModelProperty("更新者")
    private String updateBy;
    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status = true;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;
}
