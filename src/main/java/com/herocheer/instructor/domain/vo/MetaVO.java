
package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单树
 *
 * @author gaorh
 * @create 2021-01-14
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaVO {
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("隐现")
    private Boolean hidden;
}
