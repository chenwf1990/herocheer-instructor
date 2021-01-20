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
 * 功能操作VO
 *
 * @author gaorh
 * @create 2021-01-19
 */
@ApiModel(description = "功能操作表VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOperationVO {
    private Long  id;
    @NotNull(message = "菜单ID不能为空")
    @ApiModelProperty("父ID")
    private Long pid;
    @ApiModelProperty("图标")
    private String icon;
    @NotBlank(message = "操作名称不能为空")
    @ApiModelProperty(" 查看、添加、 编辑、 详情、 删除")
    private String operationName;
    @ApiModelProperty("系统管理  [100000]| – 菜单管理 [100100]   | – 角色架构  [100200] | — 消息管理  [110000] | —订单消息 [110100]  ")
    private String code;
    @ApiModelProperty("序号")
    private Integer orderNo;

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
