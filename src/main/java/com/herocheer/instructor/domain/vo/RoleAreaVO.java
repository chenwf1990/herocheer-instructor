package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 角色区域VO
 *
 * @author gaorh
 * @create 2021-01-21
 */
@ApiModel(description = "角色区域VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAreaVO {
    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
    @ApiModelProperty("区域ID")
    @NotBlank(message = "区域ID不能为空")
    private String areaId;
}
