package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc 器材借用 
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class EquipmentBorrow extends BaseEntity {
    @ApiModelProperty("借用单号")
    private String borrowReceipt;
    @ApiModelProperty("驿站id")   
    private Long courierId;
    @ApiModelProperty("驿站名")   
    private String courierName;
    @ApiModelProperty("借用人姓名")
    private String borrower;
    @ApiModelProperty("性别 (0.未知 1.男 2.女)")   
    private Integer gender;
    @ApiModelProperty("身份证号码")   
    private String identityNumber;
    @ApiModelProperty("手机号")   
    private String phoneNumber;

    @ApiModelProperty("借用日期")
    private Long borrowDate;
    @ApiModelProperty("借用时段开始")
    private String borrowTimeRangeStart;
    @ApiModelProperty("借用时段结束")
    private String borrowTimeRangeEnd;

    @ApiModelProperty("状态 (0.待审核 1.待签收 2.待归还 3.已归还 4.驳回 5.已过期 6.已取消)")
    private Integer status;
    @ApiModelProperty("归还状态 (0.默认 1.申请归还 2.已归还待用户确认 3.已确认)")
    private Integer remandStatus;
    @ApiModelProperty("归还时间")   
    private Long remandTime;
    @ApiModelProperty("借用器材")   
    private String borrowEquipment;
    @ApiModelProperty("归还器材")   
    private String remandEquipment;
    @ApiModelProperty("借出人id")
    private Long lenderId;

    @ApiModelProperty("驳回原因")
    private String rejectReason;

    @ApiModelProperty("数据来源：1-线下借出，2-线上借出")
    private Integer source;
}