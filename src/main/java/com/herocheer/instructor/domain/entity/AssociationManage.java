package com.herocheer.instructor.domain.entity;

import com.herocheer.common.base.entity.BaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwf
 * @desc 协会管理
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class AssociationManage extends BaseEntity {
    @ApiModelProperty("协会名")   
    private String name;
    @ApiModelProperty("协会类型 (1.协会 2.民非 3.其他)")   
    private Integer type;
    @ApiModelProperty("协会负责人")   
    private String principal;
    @ApiModelProperty("联系电话")   
    private String contactNumber;
    @ApiModelProperty("区域编码")   
    private String areaCode;
    @ApiModelProperty("区域名")   
    private String areaName;
    @ApiModelProperty("协会地址")   
    private String address;
    @ApiModelProperty("经度")   
    private String longitude;
    @ApiModelProperty("纬度")   
    private String latitude;
    @ApiModelProperty("协会图片路径")   
    private String imageUrl;
    @ApiModelProperty("协会说明")   
    private String description;

}