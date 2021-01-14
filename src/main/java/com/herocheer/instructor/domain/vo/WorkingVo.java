package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/11
 * @company 厦门熙重电子科技有限公司
 */
@ApiModel("排班新增实体")
@Data
public class WorkingVo extends WorkingSchedule {
    @ApiModelProperty("排班人员信息")
    private List<WorkingScheduleUser> workingScheduleUsers;

}
