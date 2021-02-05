package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 微信用户VO
 *
 * @author gaorh
 * @create 2021-01-19
 */
@ApiModel(description = "微信用户VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatUserVO extends User {
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;
}
