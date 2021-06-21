package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.SysOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 菜单VO
 *
 * @author gaorh
 * @create 2021-01-14
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuVO {

    private Long id;
    @ApiModelProperty("父菜单")
    private Long pid = 0L;
    @ApiModelProperty("菜单名")
    @NotBlank(message = "菜单名不能为空")
    private String menuName;
    @ApiModelProperty("系统管理  [100000]")
    private String code;
    @ApiModelProperty(name = "菜单URL",example="/test/test")
    @NotBlank(message = "菜单URL不能为空")
    private String url;
    @ApiModelProperty("序号")
    private Integer orderNo;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("更新者ID")
    private Long updateId;
    @ApiModelProperty("更新者")
    private String updateBy;
    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status = false;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;

    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;

    @ApiModelProperty("操作功能")
    private List<SysOperation> sysOperationList;

    @ApiModelProperty("系统标识：1-社会体育指导员，2-全民健身设施场地")
    private Integer mark;
}
