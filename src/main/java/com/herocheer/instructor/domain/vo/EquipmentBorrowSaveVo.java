package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.EquipmentBorrow;
import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: herocheer-instructor
 * @description: 器材借用保存VO
 * @author: Linjf
 * @create date: 2021-04-20 16:47
 **/
@Data
public class EquipmentBorrowSaveVo extends EquipmentBorrow {

    @ApiModelProperty("借用器材信息")
    List<EquipmentBorrowDetails> borrowDetails;
}
