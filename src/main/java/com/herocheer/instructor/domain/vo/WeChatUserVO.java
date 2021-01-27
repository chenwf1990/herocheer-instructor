package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 微信用户VO
 *
 * @author gaorh
 * @create 2021-01-19
 */
@ApiModel(description = "微信用户VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatUserVO {
    private Long id;
    @ApiModelProperty("头像")
    private String imgUrl;
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("性别：0-未知、1-男、2-女")
    private Integer sex;
    @ApiModelProperty("1：公众号用户，2：指导员")
    private Integer userType;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机号码")
    private String phoneNo;
    @ApiModelProperty("用户来源：1-从i厦门绑定的用户；2-后台系统录入的")
    private String source;
    @ApiModelProperty("证件类型")
    private String certificateType;
    @ApiModelProperty("证件号码")
    private String certificateNo;
    @ApiModelProperty("小程序（miniWeChat）、公众号（publicWeChat）")
    private String channel;
    @NotBlank(message = "openid不能为空")
    @ApiModelProperty("公众号openid")
    private String openid;
    @ApiModelProperty("保险状态: 0-未购买 1-待审核 2-审核不通过 3-未生效 4-已生效 5-已过期")
    private Integer insuranceStatus;
    @ApiModelProperty("是否已签署承诺书：0-否；1-是")
    private Boolean commitmentStatus;
    @ApiModelProperty("i厦门用户唯一标识id")
    private String ixmUserId;
    @ApiModelProperty("i厦门用户名")
    private String ixmUserName;
    @ApiModelProperty("i厦门是否实名")
    private String ixmRealNameLevel;
    @ApiModelProperty("i厦门用户真实姓名")
    private String ixmUserRealName;
    @ApiModelProperty("i厦门微信公众号登录状态")
    private Boolean ixmLoginStatus;
    @ApiModelProperty("i厦门token")
    private String ixmToken;

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
}
