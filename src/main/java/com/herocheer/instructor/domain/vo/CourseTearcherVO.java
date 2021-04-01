package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 授课老师VO
 *
 * @author gaorh
 * @create 2021-03-30
 */
@ApiModel(description = "授课老师VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTearcherVO {
    private Long id;
    @ApiModelProperty("授课老师姓名")
    @NotBlank(message = "授课老师姓名")
    private String tearcherName;
    @ApiModelProperty("性别 0未知1男2女")
    @NotNull(message = "性别 0未知1男2女")
    private Integer sex;
    @ApiModelProperty("手机号码")
    @NotBlank(message = "手机号码")
    private String phone;
    @ApiModelProperty("证件类型")
    @NotBlank(message = "证件类型")
    private String cardType;
    @ApiModelProperty("证件号码")
    @NotBlank(message = "证件号码")
    private String cardNo;
    @ApiModelProperty("指导项目")
    @NotBlank(message = "指导项目")
    private String guideProject;
    @ApiModelProperty("备注")
    private String remark;

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

    @NotBlank(message = "证书等级")
    @ApiModelProperty("证书等级")
    private String certificateGrade;
}
