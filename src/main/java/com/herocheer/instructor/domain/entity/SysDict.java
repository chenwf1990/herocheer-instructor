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
 * @desc 系统字典表
 * @date 2021-01-07
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "后台字典表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysDict extends BaseEntity {
    @ApiModelProperty("父ID")
    private String pid;
    @ApiModelProperty("字典名称")
    private String dictName;
    @ApiModelProperty("字典编码使用汉字拼音，如：订单状态——DTZT")
    private String dictCode;
    @ApiModelProperty("排序")
    private String sortNo;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;

}