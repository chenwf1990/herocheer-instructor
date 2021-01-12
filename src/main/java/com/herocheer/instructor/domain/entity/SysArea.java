package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 区域管理（省市区街道社区）
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class SysArea extends BaseEntity {
    @ApiModelProperty("父ID")   
    private Long pid;
    @ApiModelProperty("区域名称")   
    private String areaName;
    @ApiModelProperty("省-市-区-街道-社区")   
    private String areaCode;
    @ApiModelProperty("行政编码——350000000000：福建省")   
    private String chinaCode;
    @ApiModelProperty("级别—— 0：国家、1：省、2：市、3：区 4：街道 5：社区")
    private String level;
    @ApiModelProperty("备注")   
    private String remark;

}