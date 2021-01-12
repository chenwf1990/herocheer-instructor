package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 排班表人员表
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class WorkingScheduleUser extends BaseEntity {
    @ApiModelProperty("值班编号")   
    private String dutyNo;
    @ApiModelProperty("驿站排班id")   
    private Long workingScheduleId;
    @ApiModelProperty("排班用户类型 (1站长 2固定排班人员 3预约)")   
    private Integer type;
    @ApiModelProperty("用户id")   
    private Long userId;
    @ApiModelProperty("用户名称")   
    private String userName;
    @ApiModelProperty("签到时间")   
    private Long signInTime;
    @ApiModelProperty("签退时间")   
    private Long signOutTime;
    @ApiModelProperty("服务时长 (单位：分)")   
    private Integer serviceTime;
    @ApiModelProperty("备注")   
    private String remarks;

}