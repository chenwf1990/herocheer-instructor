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
 * @desc 课表信息
 * @date 2021/05/14
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "课表信息表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CourseSchedule extends BaseEntity {
    @ApiModelProperty("课程信息ID")
    private Long courseId;
    @ApiModelProperty("设置类型:1-每周，2-每天")
    private Long configType;
    @ApiModelProperty("课程日:1-周一，2-周二，3-周三，4-周四，5-周五，6-周六，0-周日")
    private Integer courseDay;
    @ApiModelProperty("课程日期")
    private Long courseDate;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    @ApiModelProperty("限招人数")
    private Long limitNumber;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("取消状态: 1-未取消，2-已取消")
    private Integer cancelStatus;
    @ApiModelProperty("取消原因")
    private String cancelReason;
    @ApiModelProperty("取消时间")
    private Long cancelTime;

}