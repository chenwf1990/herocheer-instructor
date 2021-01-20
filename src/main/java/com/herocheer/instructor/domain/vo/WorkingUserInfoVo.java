package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author chenwf
 * @desc 用户值班信息
 * @date 2021/1/20
 * @company 厦门熙重电子科技有限公司
 */
@Data
@ApiModel("值班信息")
public class WorkingUserInfoVo {
    @ApiModelProperty("值班信息")
    private List<WorkingUserVo> workingUserVos;
    @ApiModelProperty("值班状态 0.正常签到 1.异常签到 2.待完成")
    private Integer status;
    @ApiModelProperty("值班日期文本")
    private String scheduleTimeText;
}
