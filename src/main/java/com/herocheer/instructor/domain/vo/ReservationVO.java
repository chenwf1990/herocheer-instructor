package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 预约信息VO
 *
 * @author gaorh
 * @create 2021-04-19
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationVO implements Serializable {
    private static final long serialVersionUID = -6653272917103084124L;

    @ApiModelProperty("主键")
    private Long id;

    @NotEmpty(message = "课程ID不能为空")
    @ApiModelProperty("课程ID")
    private Long courseId;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("身份证号")
    private String certificateNo;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("保险状态：0-未购买 1-待审核 2-审核不通过 3-未生效 4-已生效 5-已过期")
    private Integer insuranceStatus;
    @ApiModelProperty("携带关系：0-本人，1-儿子，2-女儿")
    private Integer relationType;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;
}
