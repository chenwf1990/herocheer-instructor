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
 * @author chenwf
 * @desc 后台角色表
 * @date 2021-01-07
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "后台角色表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysRole extends BaseEntity {
    @ApiModelProperty("角色名")
    private String roleName;
    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;

    @ApiModelProperty("系统标识：1-社会体育指导员，2-全民健身设施场地")
    private Integer mark;
}