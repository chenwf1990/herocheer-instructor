package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    @ApiModelProperty("授课老师名")
    private String lecturerTeacherName;
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
    @ApiModelProperty("审批(0.待审核 1.审批通过 2.驳回 3.撤回 4.草稿)")
    private Integer approvalStatus;
    @ApiModelProperty("审批意见")   
    private String approvalComments;
    @ApiModelProperty("是否在banner显示 (0.否 1.是)")
    private Integer showBanner;
    @ApiModelProperty("是否置顶 (0.否 1.是)")
    private Integer placedTop;
    @ApiModelProperty("部门名称")   
    private String deptName;
    @ApiModelProperty("部门id")   
    private Long deptId;
    @ApiModelProperty("课程状态(0.未开始1.报名中2.报名截止3.上课中4.已结课5.课程取消)")
    private Integer state;
    @ApiModelProperty("审批人名称")
    private String approvalBy;
    @ApiModelProperty("审批时间")
    private Long approvalTime;
    @ApiModelProperty("审批人id")
    private Long approvalId;

    @ApiModelProperty("授课老师ID")
    private Long tearcherId;
    @ApiModelProperty("授课老师姓名")
    private String tearcherName;
}