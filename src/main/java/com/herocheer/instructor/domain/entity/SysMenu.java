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
 * @desc 菜单表
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "菜单表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysMenu extends BaseEntity {
    @ApiModelProperty("父菜单")
    private Long pid;
    @ApiModelProperty("菜单名")
    private String menuName;
    @ApiModelProperty("系统管理  [100000]")
    private String code;
    @ApiModelProperty("菜单URL")
    private String url;
    @ApiModelProperty("序号")
    private Integer orderNo;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;
}