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
 * @desc 后台用户操作日志表，如：后台登录、指导员入驻
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "操作日志表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysOperationLog extends BaseEntity {
    @ApiModelProperty("业务主键ID")
    private Long bizId;
    @ApiModelProperty("用户IP")
    private String ip;
    @ApiModelProperty("业务模块")
    private String module;
    @ApiModelProperty("后台登录、密码修改")
    private String bizType;
    @ApiModelProperty("操作内容")
    private String context;
    @ApiModelProperty("URI")
    private String uri;
    @ApiModelProperty("请求参数")
    private String request;
    @ApiModelProperty("响应参数")
    private String response;
    @ApiModelProperty("0：关闭、1：启用")
    private Boolean status;

}