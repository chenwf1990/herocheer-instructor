package com.herocheer.instructor.controller;

import com.herocheer.instructor.service.ActivityReplaceCardService;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

/**
 * @author makejava
 * @desc  活动补卡(ActivityReplaceCard)表控制层
 * @date 2021-01-15 17:24:55
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("")
@Api(tags = "")
public class ActivityReplaceCardController extends BaseController{
    @Resource
    private ActivityReplaceCardService activityReplaceCardService;

    

}