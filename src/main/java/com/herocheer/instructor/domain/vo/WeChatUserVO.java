package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class WeChatUserVO{

    private Long id;
    @ApiModelProperty("创建人名称")
    private String createdBy;
    @ApiModelProperty("创建时间")
    private Long createdTime;
    @ApiModelProperty("创建人id")
    private Long createdId;
    @ApiModelProperty("更新者名称")
    private String updateBy;
    @ApiModelProperty("更新时间")
    private Long updateTime;
    @ApiModelProperty("更新者id")
    private Long updateId;
    @ApiModelProperty("token")
    private String tokenId;


    @ApiModelProperty("头像")
    private String imgUrl;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("账号名称")
    private String account;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("性别：0-未知、1-男、2-女")
    private Integer sex;
    @ApiModelProperty("用户类型：1：公众号用户、2：指导员、3：后台用户、4：后台管理员")
    private Integer userType;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("用户来源：1-从i厦门绑定的用户；2-后台系统录入的")
    private String source;
    @ApiModelProperty("证件号码")
    private String certificateNo;
    @ApiModelProperty("小程序（miniWeChat）、公众号（publicWeChat）")
    private String channel;
    @ApiModelProperty("生日")
    private String birthday;
    @ApiModelProperty("住址")
    private String address;
    @ApiModelProperty("邮编")
    private String postcode;
    @ApiModelProperty("公众号UnionID")
    private String openid;
    @ApiModelProperty("微信开放平台UnionID")
    private String unionID;
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
    @ApiModelProperty("i厦门微信公众号登录状态， 0-登出；1-登录")
    private Boolean ixmLoginStatus;
    @ApiModelProperty("i厦门token")
    private String ixmToken;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;
    @ApiModelProperty("指导项目")
    private String guideProject;

    @ApiModelProperty("机构ID")
    private String deptId;
    @ApiModelProperty("机构名称")
    private String deptName;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;
}
