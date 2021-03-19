package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc banner管理
 * @date 2021-03-19 08:57:35
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class Banner extends BaseEntity {
    @ApiModelProperty("banner名称")   
    private String name;
    @ApiModelProperty("banner位置 1首页")   
    private Integer position;
    @ApiModelProperty("排序")   
    private Integer sort;
    @ApiModelProperty("图片地址")   
    private String pic;
    @ApiModelProperty("是否上下架 0上架 1下架")   
    private Integer isPublic;
    @ApiModelProperty("链接方式 1url 2课程 3驿站招募 4赛事招募 5新闻")   
    private Integer linkType;
    @ApiModelProperty("链接值")   
    private String linkValue;

}