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
 * @desc 机构表、部门表、部门架构表
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "机构表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysDept extends BaseEntity {
    @ApiModelProperty("父级id")
    private Long pid;
    @ApiModelProperty("部门名称或下属机构名称")
    private String deptName;
    @ApiModelProperty("部门级别：Level的设计原则：0.*.* 其中，0是顶级结构，第一个*是顶级结构下的结构，第二个*是顶级结构下的结构下的结构")
    private String level;
    @ApiModelProperty("排序号")
    private Long sortNo;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;

}