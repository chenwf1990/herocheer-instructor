package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 成员VO
 *
 * @author gaorh
 * @create 2021-01-21
 */
@ApiModel(description = "系统人员VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("用户类型")
    private Integer userType;
    @ApiModelProperty("证件号码")
    private String certificateNo;

    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status = true;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;

}
