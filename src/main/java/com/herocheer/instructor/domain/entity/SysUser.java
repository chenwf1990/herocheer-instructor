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
 * @desc 后台用户表（管理员、用户）
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "后台用户表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUser extends BaseEntity {
    @ApiModelProperty("机构ID")
    private Long deptId;
    @ApiModelProperty("账号名称")
    private String account;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("管理员（sysAdmin）、用户（sysUser）、")
    private String userType;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;
}