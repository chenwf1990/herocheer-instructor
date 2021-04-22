package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 器材信息 
 * @date 2021-04-19 17:18:25
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class EquipmentInfo extends BaseEntity {
    @ApiModelProperty("驿站Id")   
    private Long courierId;
    @ApiModelProperty("驿站名")   
    private String courierName;
    @ApiModelProperty("器材名")   
    private String equipmentName;
    @ApiModelProperty("规格型号")   
    private String model;
    @ApiModelProperty("品牌id")   
    private Long brandId;
    @ApiModelProperty("品牌名")   
    private String brandName;
    @ApiModelProperty("库存数量")   
    private Integer stockNumber;
    @ApiModelProperty("备注")   
    private String remarks;

}