package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc 用户指导项目VO
 * @date 2021/1/22
 * @company 厦门熙重电子科技有限公司
 */
@Data
@ApiModel("用户指导项目VO")
public class UserGuideProjectVo {
    @ApiModelProperty("用户id")
    private Long id;
    @ApiModelProperty("姓名")
    private String userName;
    @ApiModelProperty("用户指导项目")
    private String guideProject;
    @ApiModelProperty("指导员证书等级")
    private String certificateGrade;
    @ApiModelProperty("指导员指导项目")
    private String instructorGuideProject;

}
