package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 后台用户VO
 *
 * @author gaorh
 * @create 2021-01-11
 */
@ApiModel(description = "后台用户VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVO {

    private Long id;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("机构ID")
    private String deptId;
    @ApiModelProperty("机构名称")
    private String deptName;

    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty("用户类型：1：公众号用户、2：指导员、3：后台用户、4：后台管理员")
    private Integer userType;

    @ApiModelProperty("更新者ID")
    private Long updateId;
    @ApiModelProperty("更新者")
    private String updateBy;
    @ApiModelProperty("更新时间")
    private Long updateTime;
    @ApiModelProperty("角色id")
    private String roleId;

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

    @ApiModelProperty("指导项目")
    private String guideProject;


    @ApiModelProperty("系统标识：1-社会体育指导员，2-全民健身设施场地")
    private Integer mark;
}
