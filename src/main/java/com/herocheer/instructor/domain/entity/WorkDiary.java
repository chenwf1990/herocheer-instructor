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
 * @desc 工作日志
 * @date 2021/06/07
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel(description = "工作日志表")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkDiary extends BaseEntity {
    @ApiModelProperty("登记人")
    private String registerName;
    @ApiModelProperty("日志类型:1-驿站值班，2-公益课程")
    private Object diaryType;
    @ApiModelProperty("值班日期")
    private Long workingDate;
    @ApiModelProperty("业务ID（根据日志类型变动）")
    private Long objectId;
    @ApiModelProperty("业务信息（驿站名，课程名）")
    private Long objectInfo;
    @ApiModelProperty("工作内容描述")
    private String workDescribe;
    @ApiModelProperty("照片上传")
    private String images;
    @ApiModelProperty("备注")
    private String remark;

}
