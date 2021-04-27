package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.EquipmentInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-04-25 16:46
 **/
@Data
public class EquipmentInfoVo extends EquipmentInfo {
    @ApiModelProperty("原表id")
    private Long equipmentId;
    @ApiModelProperty("报废Id")
    private Long damageId;

}
