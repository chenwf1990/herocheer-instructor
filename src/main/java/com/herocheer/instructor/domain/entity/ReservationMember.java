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
 * @desc
 * @date 2021/04/20
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "课程报名人员表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReservationMember extends BaseEntity {
    @ApiModelProperty("课程ID")
    private Long relevanceId;
    @ApiModelProperty("报名中的当前用户ID")
    private Long userId;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("性别：0-未知、1-男、2-女")
    private Integer sex;
    @ApiModelProperty("身份证号")
    private String identityNumber;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("保险状态: 0-未购买 1-待审核 2-审核不通过 3-未生效 4-已生效 5-已过期")
    private Integer insuranceStatus;
    @ApiModelProperty("携带关系：0-本人，1-儿子，2-女儿")
    private Integer relationType;
    @ApiModelProperty("备注")
    private String remark;
}