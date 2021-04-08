package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 协会成员
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class AssociationMember extends BaseEntity {
    @ApiModelProperty("协会Id")   
    private Long associationId;
    @ApiModelProperty("协会名")   
    private String associationName;
    @ApiModelProperty("成员名")   
    private String name;
    @ApiModelProperty("性别 (1.男 2.女)")   
    private Integer gender;
    @ApiModelProperty("证件号")   
    private String licenseNumber;
    @ApiModelProperty("联系电话")   
    private String contactNumber;
    @ApiModelProperty("职务 (0.普通成员 1.秘书长 2.副会长 3.会长)")   
    private Integer jobTitle;

}