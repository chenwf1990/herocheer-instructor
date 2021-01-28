package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.CourierStation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class CourierStationVo extends CourierStation {
    @ApiModelProperty("收藏数量")
    private int collectNum;
}
