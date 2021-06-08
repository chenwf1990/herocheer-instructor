package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description: 器材归还列表
 * @author: Linjf
 * @create date: 2021-04-21 14:42
 **/
@Data
public class EquipmentRemandVo extends EquipmentBorrowDetails {
    @ApiModelProperty("归还id")
    private String remandId;

    @ApiModelProperty("本次归还数量")
    private Integer thisRemandQuantity;

    @ApiModelProperty("器材图片")
    private String image;
}
