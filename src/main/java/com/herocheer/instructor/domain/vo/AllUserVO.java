package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author gaorh
 * @create 2021-06-25
 */

@ApiModel(description = "用户表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AllUserVO extends User {
    @ApiModelProperty("角色名")
    private String roleName;
}
