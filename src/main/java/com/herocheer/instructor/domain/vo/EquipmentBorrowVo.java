package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.EquipmentBorrow;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-04-22 09:03
 **/
@Data
public class EquipmentBorrowVo extends EquipmentBorrow {
    @ApiModelProperty("器材借用记录")
    List<EquipmentBorrowDetailsVo> borrowDetails;
}
