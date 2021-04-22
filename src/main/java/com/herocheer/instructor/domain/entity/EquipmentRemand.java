package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 器材归还记录 
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class EquipmentRemand extends BaseEntity {
    @ApiModelProperty("借用单据id")   
    private Long borrowId;
    @ApiModelProperty("关联借用器材id")   
    private Long borrowDetailsId;
    @ApiModelProperty("归还数量")   
    private Integer remandQuantity;
    @ApiModelProperty("接收人")   
    private String receiveBy;
    @ApiModelProperty("接收人id")   
    private Long receiveId;
    @ApiModelProperty("接收时间")   
    private Long receiveTime;
    @ApiModelProperty("接收人时间号码")   
    private String phoneNumber;
    @ApiModelProperty("状态 (0.默认1.申请归还 2.已归还待用户确认 3.已确认)")   
    private Integer remandStatus;

}