package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @date 2020/4/15
 */
@Data
@ApiModel("微信登录请求数据模型")
public class WechaLoginVo {
    @ApiModelProperty("微信code")
    private String wecharCode;
    @ApiModelProperty("微信用户加密信息参数")
    private String encryptedData;
    @ApiModelProperty("解密偏移量")
    private String iv;
}
