package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-04-20 09:15
 **/
@Data
public class EquipmentInfoQueryVo implements Serializable {

    @ApiModelProperty("驿站Id")
    private Long courierId;

    @ApiModelProperty("器材名")
    private String equipmentName;

    @ApiModelProperty("品牌名")
    private String brandName;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
