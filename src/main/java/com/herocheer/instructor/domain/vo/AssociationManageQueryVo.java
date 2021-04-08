package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description: 协会管理查询VO
 * @author: Linjf
 * @create date: 2021-04-07 09:56
 **/
@Data
public class AssociationManageQueryVo implements Serializable {

    @ApiModelProperty("协会名")
    private String name;

    @ApiModelProperty("协会类型 (1.协会 2.民非 3.其他)")
    private Integer type;

    @ApiModelProperty("区域编码")
    private String areaCode;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
