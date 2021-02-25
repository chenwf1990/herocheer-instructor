package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
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
 * @desc 微信用户、后台用户、后台管理员
 * @date 2021-01-22 15:01:45
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "用户表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity {
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

    @ApiModelProperty("工作单位")
    private String workUnit;

    public User(WeChatUserVO userPO) {
        this.setId(userPO.getId());
        this.setIxmUserId(userPO.getIxmUserId());
        this.setIxmUserName(userPO.getIxmUserName());
        this.setIxmRealNameLevel(userPO.getIxmRealNameLevel());
        this.setIxmUserRealName(userPO.getIxmUserRealName());
        this.setImgUrl(userPO.getImgUrl());
        this.setUserName(userPO.getUserName());
        this.setSex(userPO.getSex());
        this.setCertificateNo(userPO.getCertificateNo());
        this.setPhone(userPO.getPhone());
        this.setEmail(userPO.getEmail());
        this.setOpenid(userPO.getOpenid());
        this.setSource(userPO.getSource());
        this.setAge(userPO.getAge());
        this.setInsuranceStatus(userPO.getInsuranceStatus());
        this.setCommitmentStatus(userPO.getCommitmentStatus());
        this.setIxmLoginStatus(userPO.getIxmLoginStatus());
        this.setIxmToken(userPO.getIxmToken());
    }
}