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
 * @author gaorh
 * @desc 场地采集人员表
 * @date 2021/06/01
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "场地采集人员表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FieldMember extends BaseEntity {
    @ApiModelProperty("采集人编号")
    private String memberId;
    @ApiModelProperty("采集人姓名")
    private String memberName;
    @ApiModelProperty("所属区")
    private String area;
    @ApiModelProperty("所属街镇")
    private String street;
    @ApiModelProperty("所属村居")
    private String village;
    @ApiModelProperty("信息员分级")
    private String grade;
    @ApiModelProperty("负责范围简略说明（5级息员填写）")
    private String remark;
}