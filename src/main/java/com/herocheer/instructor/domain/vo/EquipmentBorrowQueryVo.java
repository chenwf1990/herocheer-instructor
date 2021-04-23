package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 器材归还查询VO
 * @author: Linjf
 * @create date: 2021-04-20 15:40
 **/

@Data
public class EquipmentBorrowQueryVo implements Serializable {

    @ApiModelProperty("驿站id")
    private Long courierId;

    @ApiModelProperty("借用单号")
    private String borrowReceipt;

    @ApiModelProperty("借用人姓名")
    private String borrower;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("借用开始时间")
    private Long borrowStartTime;

    @ApiModelProperty("借用结束时间")
    private Long borrowEndTime;

    @ApiModelProperty("归还开始时间")
    private Long remandStartTime;

    @ApiModelProperty("归还结束时间")
    private Long remandEndTime;

    @ApiModelProperty("归还结束时间")
    private Long userId;

    @ApiModelProperty("状态 (0.待审核 1.待借出 2.待归还 3.已归还 4.驳回  5.已办结)")
    private Integer status;

    @ApiModelProperty("查询类型(0.查询全部 1.驿站值班人员查询 2.用户查询)")
    private Integer queryType;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
