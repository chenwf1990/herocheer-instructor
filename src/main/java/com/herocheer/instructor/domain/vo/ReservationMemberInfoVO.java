package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.ReservationMember;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 预约人信息VO
 *
 * @author gaorh
 * @create 2021-06-11
 */
@ApiModel(description = "预约人信息VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class ReservationMemberInfoVO extends ReservationMember {
    @ApiModelProperty("取消原因")
    private String cancelReason;
}
