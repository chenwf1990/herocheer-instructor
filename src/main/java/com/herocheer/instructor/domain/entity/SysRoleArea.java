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
 * @desc 中间表
 * @date 2021-01-07
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "中间表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysRoleArea extends BaseEntity {
    @ApiModelProperty("区域ID")
    private Long areaId;
    @ApiModelProperty("角色ID")
    private Long roleId;

}