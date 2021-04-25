package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 器材损坏 
 * @date 2021-04-25 15:31:31
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class EquipmentDamage extends BaseEntity {
    @ApiModelProperty("器材借用id")   
    private Long borrowId;
    @ApiModelProperty("状态 (0.遗失1.损坏)")   
    private Integer status;
    @ApiModelProperty("损坏原因")   
    private String damageReason;

}