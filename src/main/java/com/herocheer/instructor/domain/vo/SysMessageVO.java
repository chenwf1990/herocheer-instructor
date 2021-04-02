package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息中心VO
 *
 * @author gaorh
 * @create 2021-04-01
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMessageVO {

    public SysMessageVO(String messageContext, String messageType, String messageCode, Long objectId) {
        this.messageContext = messageContext;
        this.messageType = messageType;
        this.messageCode = messageCode;
        this.objectId = objectId;
    }

    private Long id;
    @ApiModelProperty("消息内容")
    private String messageContext;
    @ApiModelProperty("消息类型")
    private String messageType;
    @ApiModelProperty("消息编码")
    private String messageCode;

    @ApiModelProperty("办理状态")
    private Boolean handleStatus;
    @ApiModelProperty("业务ID")
    private Long objectId;
    @ApiModelProperty("阅读状态(0-未读,1-已读)")
    private Boolean ReadStatus;

    @ApiModelProperty("更新者ID")
    private Long updateId;
    @ApiModelProperty("更新者")
    private String updateBy;
    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;

    @ApiModelProperty("页码")
    private int pageNo = 1;
    @ApiModelProperty("页数")
    private int pageSize = 10;
}
