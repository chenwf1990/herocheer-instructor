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
 * @desc 场地设施表
 * @date 2021/04/21
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "场地设施表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Field extends BaseEntity {
    @ApiModelProperty("场地名称")
    private String fieldName;
    @ApiModelProperty("所属区域")
    private String areaName;
    @ApiModelProperty("区域编码")
    private String areaCode;
    @ApiModelProperty("安装地址")
    private String address;
    @ApiModelProperty("经度")
    private String longitude;
    @ApiModelProperty("纬度")
    private String latitude;
    @ApiModelProperty("场地类型")
    private String fieldType;
    @ApiModelProperty("场地类型编码")
    private String fieldTypeCode;
    @ApiModelProperty("场地项目")
    private String fieldItem;
    @ApiModelProperty("其他说明")
    private String other;
    @ApiModelProperty("建设时间")
    private Long foundTime;
    @ApiModelProperty("场地设施照片")
    private String fieldImages;
    @ApiModelProperty("运营管理单位")
    private String adminUnit;
    @ApiModelProperty("管理人员姓名")
    private String adminName;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("是否正常开放：是-1，否-0")
    private Boolean openStatus;
    @ApiModelProperty("请填写未正常开放原因")
    private String noOpenReason;
    @ApiModelProperty("管理制度是否健全：是-1，否-0")
    private Boolean adminSystemStatus;
    @ApiModelProperty("是否有告示牌：有-1，否-0")
    private Boolean billboardStatus;
    @ApiModelProperty("最后一次维护时间")
    private Long lastMaintainTime;
    @ApiModelProperty(" 备注")
    private String remark;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;

}