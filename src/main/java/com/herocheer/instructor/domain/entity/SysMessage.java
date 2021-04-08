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
 * @author chenwf
 * @desc 系统消息通知
 * @date 2020/12/30
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "消息通知")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysMessage extends BaseEntity {
    @ApiModelProperty("消息内容")
    private String messageContext;
    @ApiModelProperty("消息类型")
    private String messageType;
    @ApiModelProperty("办理状态")
    private Boolean handleStatus;
    @ApiModelProperty("业务ID")
    private Long objectId;
    @ApiModelProperty("阅读状态(0-未读,1-已读)")
    private Boolean ReadStatus;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("消息编码")
    private String messageCode;
}