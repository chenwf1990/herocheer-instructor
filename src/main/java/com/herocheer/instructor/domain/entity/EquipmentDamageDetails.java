package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 器材损坏关联借用信息表 
 * @date 2021-04-25 15:31:31
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class EquipmentDamageDetails extends BaseEntity {
    @ApiModelProperty("损坏id")   
    private Long damageId;
    @ApiModelProperty("借用详情Id")   
    private Long borrowDetailsId;
    @ApiModelProperty("损坏数量")   
    private Integer damagQuantity;

}