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
 * @author gaorh
 * @desc 场地采集人员
 * @date 2021/04/21
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "场地采集人员表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FieldMember extends BaseEntity {
    @ApiModelProperty("采集人编号")
    private String memberId;
    @ApiModelProperty("采集人姓名")
    private String memberName;
    @ApiModelProperty("性别：0-未知、1-男、2-女")
    private String sex;
    @ApiModelProperty("身份证号")
    private String certificateNo;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
}