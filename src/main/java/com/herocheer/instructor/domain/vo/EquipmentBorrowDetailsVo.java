package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.instructor.domain.entity.EquipmentRemand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-04-22 09:06
 **/
@Data
public class EquipmentBorrowDetailsVo extends EquipmentBorrowDetails {
    @ApiModelProperty("器材归还记录")
    List<EquipmentRemand> remands;
}
