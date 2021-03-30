package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author chenwf
 * @desc 授课老师
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "授课老师")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CourseTearcher extends BaseEntity {
    @ApiModelProperty("授课老师姓名")
    private String tearcherName;
    @ApiModelProperty("性别 0未知1男2女")
    private Integer sex;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("证件类型")
    private String cardType;
    @ApiModelProperty("证件号码")
    private String cardNo;
    @ApiModelProperty("指导项目")
    private String guideProject;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;

}