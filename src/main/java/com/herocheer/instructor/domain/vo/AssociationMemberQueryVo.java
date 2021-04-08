package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-04-07 14:22
 **/
@Data
public class AssociationMemberQueryVo {
    @ApiModelProperty("协会Id")
    private Long associationId;

    @ApiModelProperty("成员名")
    private String name;

    @ApiModelProperty("联系电话")
    private String contactNumber;

    @ApiModelProperty("页码")
    private int pageNo;

    @ApiModelProperty("页数")
    private int pageSize;
}
