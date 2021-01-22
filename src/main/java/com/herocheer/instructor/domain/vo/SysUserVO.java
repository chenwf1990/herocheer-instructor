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
    private Long deptId;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("用户姓名")
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @ApiModelProperty("管理员（sysAdmin）、用户（sysUser）")
    private String userType;
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
}
