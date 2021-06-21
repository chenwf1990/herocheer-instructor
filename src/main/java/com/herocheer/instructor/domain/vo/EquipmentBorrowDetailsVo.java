package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.instructor.domain.entity.EquipmentRemand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;

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

    @ApiModelProperty("单价")
    private Long price;

    @ApiModelProperty("器材图片")
    private String image;

    @ApiModelProperty("器材单位")
    private String unit;


    public EquipmentBorrowDetails voToEntity(EquipmentBorrowDetailsVo vo){
        EquipmentBorrowDetails entity =new EquipmentBorrowDetails();
        BeanCopier.create(vo.getClass(),entity.getClass(),false).copy(vo,entity,null);
        return entity;
    }
}
