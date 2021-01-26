package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author gaorh
 * @create 2021-01-25
 */
@ApiModel(description = "操作日志VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOperationLogVO {
    private Long id;
    @ApiModelProperty("业务主键ID")
    private Long bizId;
    @ApiModelProperty("用户ip")
    private String ip;
    @ApiModelProperty("模块")
    private String module;
    @ApiModelProperty("操作类型：后台登录、密码修改")
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
    private Boolean status = true;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;
}
