package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * 角色菜单关联VO
 *
 * @author gaorh
 * @create 2021-01-20
 */
@ApiModel(description = "角色菜单关联VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuVO {

    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
    @ApiModelProperty("菜单ID")
    private String menuId;
}
