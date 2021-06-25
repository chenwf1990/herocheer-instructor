package com.herocheer.instructor.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程服务时长统计VO
 *
 * @author gaorh
 * @create 2021-06-09
 */
@ExcelTarget("courseStatis")
@ApiModel(description = "课程服务时长统计VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseStatisVO {
    @ApiModelProperty("课程ID")
    private Long courseId;

    @Excel(name = "课程名称", orderNum = "1")
    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("授课老师ID")
    private Long lecturerTeacherId;

    @Excel(name = "授课老师姓名", orderNum = "2", width = 25)
    @ApiModelProperty("授课老师姓名")
    private String lecturerTeacherName;

    @ApiModelProperty("开课日期")
    private Long courseStartTime;

    /**
     * 导出功能使用
     **/
    @Excel(name = "开课日期", orderNum = "3", width = 25,format = "yyyy-MM-dd")
    @ApiModelProperty("开课日期(excel)")
    private String excelCourseStartTime;

    @Excel(name = "证书等级", orderNum = "4")
    @ApiModelProperty("证书等级")
    private String certificateGrade;

    @Excel(name = "指导项目", orderNum = "5", width = 20)
    @ApiModelProperty("指导项目")
    private String guideProject;

    @Excel(name = "服务时长（分钟）", orderNum = "6")
    @ApiModelProperty("服务时长")
    private Integer serviceTimeTotal;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private Integer pageNo = 1;
    @ApiModelProperty("页数")
    private Integer pageSize = 10;
}
