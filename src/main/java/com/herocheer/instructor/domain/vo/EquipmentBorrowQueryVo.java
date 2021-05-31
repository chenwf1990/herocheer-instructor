package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    @ApiModelProperty("借用日期开始")
    private Long borrowDateStart;

    @ApiModelProperty("借用日期结束")
    private Long borrowDateEnd;

    @ApiModelProperty("归还开始时间")
    private Long remandStartTime;

    @ApiModelProperty("归还结束时间")
    private Long remandEndTime;

    @ApiModelProperty("归还结束时间")
    private Long userId;

    @ApiModelProperty("状态 (0.待审核 1.待借出 2.待归还 3.已归还 4.驳回  5.已办结 7.已过期 8.已取消)")
    private Integer status;

    @ApiModelProperty("查询类型(0.查询全部 1.驿站值班人员查询 2.用户查询 3.管理员查询<不做限制>)")
    private Integer queryType;

    @ApiModelProperty("驿站id集合")
    private List<Long> courierStationIds;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;


}
