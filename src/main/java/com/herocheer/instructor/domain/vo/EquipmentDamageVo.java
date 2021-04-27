package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.EquipmentDamage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-04-25 16:19
 **/
@Data
public class EquipmentDamageVo extends EquipmentDamage {
    @ApiModelProperty("损坏器材")
    List<EquipmentDamageDetailsVo> damageDetailsVos;
}
