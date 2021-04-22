package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 器材借用详情 
 * @date 2021-04-20 15:36:20
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class EquipmentBorrowDetails extends BaseEntity {
    @ApiModelProperty("借用id")   
    private Long borrowId;
    @ApiModelProperty("器材id")   
    private Long equipmentId;
    @ApiModelProperty("器材名")   
    private String equipmentName;
    @ApiModelProperty("品牌名")   
    private String brandName;
    @ApiModelProperty("规格型号")   
    private String model;
    @ApiModelProperty("借用数量")   
    private Integer borrowQuantity;
    @ApiModelProperty("实际借用数量")   
    private Integer actualBorrowQuantity;
    @ApiModelProperty("累计归还数量")
    private Integer remandQuantity;
    @ApiModelProperty("待归还数量")   
    private Integer unreturnedQuantity;
    @ApiModelProperty("归还状态(0.待归还 1.已归还)")   
    private Integer remandStatus;
    @ApiModelProperty("借出人")   
    private String borrowBy;
    @ApiModelProperty("借出人id")   
    private Long borrowUserId;
    @ApiModelProperty("借出时间")   
    private Long borrowTime;
    @ApiModelProperty("借出人手机号码")   
    private String phoneNumber;

}