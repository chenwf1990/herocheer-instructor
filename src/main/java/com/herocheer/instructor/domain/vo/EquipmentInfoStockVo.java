package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.EquipmentInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-04-22 11:50
 **/
@Data
public class EquipmentInfoStockVo extends EquipmentInfo {
    @ApiModelProperty("借出数量")
    private Integer borrowQuantity;

    @ApiModelProperty("剩余数量")
    private Integer surplusQuantity;
}
