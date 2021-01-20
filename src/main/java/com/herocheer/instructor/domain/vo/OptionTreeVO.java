package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 菜单功能树VO
 *
 * @author gaorh
 * @create 2021-01-20
 */
@ApiModel(description = "菜单功能树VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionTreeVO {

    private Long id;
    @ApiModelProperty("父ID")
    private Long pid = 0L;
    @ApiModelProperty("名称")
    private String name;
}
