package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 驿站服务时段 
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class ServiceHours extends BaseEntity {
    @ApiModelProperty("驿站Id (多个用,号分隔)")   
    private String stationIds;
    @ApiModelProperty("驿站名称 (多个用,号分隔)")   
    private String stationNames;
    @ApiModelProperty("服务时间段")   
    private String serviceTimes;
}