package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 课程信息 
 * @date 2021-02-25 09:34:57
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class CourseInfo extends BaseEntity {
    @ApiModelProperty("课程标题")   
    private String title;
    @ApiModelProperty("课程类型 (1.实践指导动作2.理论课)")   
    private Integer type;
    @ApiModelProperty("授课老师")   
    private Long lecturerTeacherId;
    @ApiModelProperty("课程开始时间")   
    private Long courseStartTime;
    @ApiModelProperty("课程结束时间")   
    private Long courseEndTime;
    @ApiModelProperty("课程举办地址")   
    private String address;
    @ApiModelProperty("经度")   
    private String longitude;
    @ApiModelProperty("纬度")   
    private String latitude;
    @ApiModelProperty("限招人数")   
    private Integer limitNumber;
    @ApiModelProperty("报名人数")   
    private Integer signNumber;
    @ApiModelProperty("报名开始时间")   
    private Long signStartTime;
    @ApiModelProperty("报名结束时间")   
    private Long signEndTime;
    @ApiModelProperty("课程描述")   
    private Object description;
    @ApiModelProperty("课程照片")   
    private String image;
    @ApiModelProperty("审批状态")   
    private Integer approvalStatus;
    @ApiModelProperty("审批意见")   
    private String approvalComments;
    @ApiModelProperty("是否在banner显示 (0.否1.是)")   
    private Integer showBanner;
    @ApiModelProperty("是否置顶 (0.否1.是)")   
    private Integer placedTop;
    @ApiModelProperty("部门名称")   
    private String deptName;
    @ApiModelProperty("部门id")   
    private Long deptId;
    @ApiModelProperty("状态 (0.待审核1.撤回2.驳回3.待报名4.报名中5.报名结束)")   
    private Integer state;

}