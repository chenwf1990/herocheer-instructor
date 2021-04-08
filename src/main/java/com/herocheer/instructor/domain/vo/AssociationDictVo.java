package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: herocheer-instructor
 * @description:协会字典vo
 * @author: Linjf
 * @create date: 2021-04-07 10:53
 **/
@Data
public class AssociationDictVo implements Serializable {

    @ApiModelProperty("协会id")
    private Long id;

    @ApiModelProperty("协会名")
    private String name;
}
