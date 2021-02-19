package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaorh
 * @create 2021-02-05
 */
@ApiModel(description = "VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuTmpVO {
    @ApiModelProperty("父菜单")
    private Long pid;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;

}
