package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.EquipmentDamageDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-04-25 16:16
 **/
@Data
public class EquipmentDamageDetailsVo extends EquipmentDamageDetails {
    @ApiModelProperty("器材名")
    private String equipmentName;
    @ApiModelProperty("品牌名")
    private String brandName;
    @ApiModelProperty("规格型号")
    private String model;
    @ApiModelProperty("未归还数量")
    private Integer unreturnedQuantity;
}
