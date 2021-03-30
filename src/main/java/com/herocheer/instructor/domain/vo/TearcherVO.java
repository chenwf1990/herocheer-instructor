package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 老师VO
 *
 * @author gaorh
 * @create 2021-03-30
 */
@ApiModel(description = "老师VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TearcherVO {
    private Long id;
    @ApiModelProperty("授课老师姓名")
    private String tearcherName;
    @ApiModelProperty("性别 0未知1男2女")
    private Integer sex;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("证件类型")
    private String cardType;
    @ApiModelProperty("证件号码")
    private String cardNo;
}
