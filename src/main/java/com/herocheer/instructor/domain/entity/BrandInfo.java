package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 品牌管理 
 * @date 2021-04-19 15:52:24
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class BrandInfo extends BaseEntity {
    @ApiModelProperty("品牌名")   
    private String brandName;
    @ApiModelProperty("编号")   
    private String numbering;
    @ApiModelProperty("是否启用 (0.启用 1.关闭)")   
    private Integer isEnable;

}