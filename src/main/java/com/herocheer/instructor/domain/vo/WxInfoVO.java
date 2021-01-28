package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @desc 微信信息
 * @author chenwf
 * @create 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class WxInfoVO {
    @ApiModelProperty("appId")
    private String appId;
    @ApiModelProperty("随机串")
    private String nonceStr;
    @ApiModelProperty("时间戳")
    private String timestamp;
    @ApiModelProperty("签名")
    private String signature;
    @ApiModelProperty("ticket")
    private String ticket;
}
