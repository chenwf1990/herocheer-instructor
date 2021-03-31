package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.FitnessVideo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc
 * @date 2021/3/17
 * @company 厦门熙重电子科技有限公司
 */
@Data
@ApiModel("健身视频数据模型")
public class FitnessVideoVo extends FitnessVideo {
    @ApiModelProperty("收藏数量")
    private int collectNum;
}
