package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author gaorh
 * @desc
 * @date 2021-01-07
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "中间表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu extends BaseEntity {
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("菜单ID")
    private Long menuId;

}