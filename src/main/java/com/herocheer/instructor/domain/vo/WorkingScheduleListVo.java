package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.WorkingSchedule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/12
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel("排班列表返回实体信息")
@Data
public class WorkingScheduleListVo extends WorkingSchedule {
    @ApiModelProperty("站长值班人员")
    private String stationUserName;
    @ApiModelProperty("固定值班人员")
    private String fixationUserName;
    @ApiModelProperty("预约值班人员")
    private String subscribeUserName;
}
