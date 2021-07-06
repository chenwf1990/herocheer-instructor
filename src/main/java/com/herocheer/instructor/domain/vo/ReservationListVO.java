package com.herocheer.instructor.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
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
@ExcelTarget("reservationList")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReservationListVO extends Reservation {
    @ApiModelProperty("携带关系：0-本人，1-儿子，2-女儿")
    private String relationType;

    @ApiModelProperty("课表预约的开始时间")
    private String courseStartTime;
    @ApiModelProperty("课表预约的结束时间")
    private String courseEndTime;

    @ApiModelProperty("取消原因")
    private String cancelReason;

    @Excel(name = "预约时间", orderNum = "5", width = 25)
    @ApiModelProperty("预约时间(excel)")
    private String excelCreatedTime;

}
