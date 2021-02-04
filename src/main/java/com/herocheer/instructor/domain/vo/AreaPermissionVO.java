package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 数据权限VO
 *
 * @author gaorh
 * @create 2021-02-03
 */
@ApiModel(description = "数据权限VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaPermissionVO {
    @ApiModelProperty("区域ID")
    private String areaId;
    @ApiModelProperty("区域编码")
    private String areaCode;
}
