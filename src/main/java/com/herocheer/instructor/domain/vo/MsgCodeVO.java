package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 短信验证码VO
 *
 * @author gaorh
 * @create 2021-03-17
 */

@ApiModel(description = "短信验证码VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgCodeVO {

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("验证码")
    private String code;

}
