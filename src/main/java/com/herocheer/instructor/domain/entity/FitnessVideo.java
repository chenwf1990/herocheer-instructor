package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 健身视频管理
 * @date 2021-03-18 15:17:12
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class FitnessVideo extends BaseEntity {
    @ApiModelProperty("健身主题")   
    private String title;
    @ApiModelProperty("内容描述")   
    private String content;
    @ApiModelProperty("排序")   
    private Integer sort;
    @ApiModelProperty("封面图片地址")   
    private String pic;
    @ApiModelProperty("视频地址")   
    private String videoUrl;
    @ApiModelProperty("状态 0启用 1禁用")   
    private Integer state;
    @ApiModelProperty("机构id")   
    private Long deptId;
    @ApiModelProperty("机构名称")   
    private String deptName;
    @ApiModelProperty("浏览数量")
    private Integer browseNum;

}