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
 * @desc 功能操作表
 * @date 2021-01-07
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "功能操作表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysOperation extends BaseEntity {
    @ApiModelProperty("父ID")
    private Long pid;
    @ApiModelProperty("菜单ID")
    private Long menuId;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty(" 查看、添加、 编辑、 详情、 删除")
    private String operationName;
    @ApiModelProperty(" 查看[get]    添加[add]   编辑[edit]  详情[detail]  删除drop]")
    private String code;
    @ApiModelProperty("序号")
    private Integer orderNo;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;
}