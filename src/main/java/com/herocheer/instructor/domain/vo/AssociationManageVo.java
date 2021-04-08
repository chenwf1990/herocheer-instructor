package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.AssociationManage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: herocheer-instructor
 * @description: 协会管理
 * @author: Linjf
 * @create date: 2021-04-07 09:54
 **/
@Data
public class AssociationManageVo extends AssociationManage {

    @ApiModelProperty("成员数")
    private Integer membersNumber;
}
