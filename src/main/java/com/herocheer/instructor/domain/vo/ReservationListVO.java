package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.Reservation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 预约列表VO
 *
 * @author gaorh
 * @create 2021-04-25
 */

@ApiModel(description = "预约列表VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReservationListVO extends Reservation {
    @ApiModelProperty("携带关系：0-本人，1-儿子，2-女儿")
    private String relationType;



}
