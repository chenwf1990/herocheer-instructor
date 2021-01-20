package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 系统角色VO
 *
 * @author gaorh
 * @create 2021-01-14
 */
@ApiModel(description = "系统角色VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleVO {
    private Long id;
    @ApiModelProperty("角色名")
    @NotBlank(message = "角色名不能为空")
    private String roleName;
    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("菜单ID")
    private String menuId ;
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
