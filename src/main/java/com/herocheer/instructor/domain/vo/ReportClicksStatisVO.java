package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.ReportClicks;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 点击量统计VO
 *
 * @author gaorh
 * @create 2021-06-08
 */

@ApiModel(description = "点击量统计VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportClicksStatisVO extends ReportClicks{

    @ApiModelProperty("点击量")
    private Integer clicksNum;

}
