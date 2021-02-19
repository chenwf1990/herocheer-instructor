package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 组织结构VO
 *
 * @author gaorh
 * @create 2021-01-15
 */
@ApiModel(description = "组织结构VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDeptVO {
    private Long id;
    @ApiModelProperty("父级id")
    private Long pid = 0L;
    @ApiModelProperty("部门名称或下属机构名称")
    private String deptName;
    @ApiModelProperty("部门级别：Level的设计原则：0.*.* 其中，0是顶级结构，第一个*是顶级结构下的结构，第二个*是顶级结构下的结构下的结构")
    private String level = "0" ;
    @ApiModelProperty("排序号")
    private Long sortNo;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status = true;
    @ApiModelProperty("上级名称")
    private String parentName;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;

    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;
}
