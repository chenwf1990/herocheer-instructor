package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.WorkingReplaceCard;
import com.herocheer.instructor.domain.entity.WorkingSignRecord;
import com.herocheer.instructor.service.WorkingReplaceCardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenwf
 * @desc  值班补卡(WorkingReplaceCard)表控制层
 * @date 2021-01-20 10:14:13
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/replaceCard")
@Api(tags = "补卡api")
public class WorkingReplaceCardController extends BaseController{
    @Resource
    private WorkingReplaceCardService workingReplaceCardService;

    @GetMapping("/getReplaceCardList")
    @ApiOperation("获取补卡信息列表")
    public ResponseResult<List<WorkingReplaceCard>> getReplaceCardList(@ApiParam("值班人员id") Long workingScheduleUserId){
        List<WorkingReplaceCard> workingReplaceCards = workingReplaceCardService.getReplaceCardList(workingScheduleUserId);
        return ResponseResult.ok(workingReplaceCards);
    }

}