package com.herocheer.instructor.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author gaorh
 * @desc 用户角色关联表
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "用户角色中间表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole{
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("角色ID")
    private Long roleId;

}