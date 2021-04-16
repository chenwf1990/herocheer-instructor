package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc 指导员证书表
 * @date 2021-03-01 15:24:13
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class InstructorApplyAuditLog extends BaseEntity {
    @ApiModelProperty("指导员申请单id")   
    private Long applyId;
    @ApiModelProperty("姓名")   
    private String name;
    @ApiModelProperty("性别 0女1男2未知")   
    private Integer sex;
    @ApiModelProperty("审核状态 0待审核1审核通过2审核驳回")   
    private Integer auditState;
    @ApiModelProperty("审核时间")   
    private Long auditTime;
    @ApiModelProperty("审核意见")   
    private String auditIdea;
    @ApiModelProperty("身份证号")   
    private String cardNo;
    @ApiModelProperty("手机号")   
    private String phone;
    @ApiModelProperty("工作单位")   
    private String workUnit;
    @ApiModelProperty("区域编码")   
    private String areaCode;
    @ApiModelProperty("区域名称")   
    private String areaName;
    @ApiModelProperty("指导项目")   
    private String guideProject;
    @ApiModelProperty("指导站点")   
    private String guideStation;
    @ApiModelProperty("证书编号")   
    private String certificateNo;
    @ApiModelProperty("证书等级")   
    private String certificateGrade;
    @ApiModelProperty("发证日期")   
    private Long openingDate;
    @ApiModelProperty("审批单位类型 WLJ_OTHER其他 WLJ_HLQ湖里区文旅局WLJ_SMQ思明区文旅局WLJ_JMQ集美区文旅局WLJ_HCQ海沧文旅局WLJ_XAQ翔安文旅局WLJ_TAQ同安文旅局WLJ_TYJ体育局")   
    private String auditUnitType;
    @ApiModelProperty("审批单位名称")   
    private String auditUnitName;
    @ApiModelProperty("其他审批单位名称")   
    private String otherAuditUnitName;
    @ApiModelProperty("证书图片 （最多3张，多张逗号隔开）")   
    private String certificatePic;
    @ApiModelProperty("渠道0pc 1H5 2小程序 3ios 4Android")   
    private Integer channel;
    @ApiModelProperty("微信openId")   
    private String openId;
    @ApiModelProperty("用户id")   
    private Long userId;
    @ApiModelProperty("头像")   
    private String headPic;
    @ApiModelProperty("备注")   
    private String remarks;

    @ApiModelProperty("证件类型")
    private String cardType;
}