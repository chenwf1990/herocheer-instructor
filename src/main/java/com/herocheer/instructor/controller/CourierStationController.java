package com.herocheer.instructor.controller;

import com.herocheer.instructor.service.CourierStationService;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

/**
 * @author chenwf
 * @desc  驿站(CourierStation)表控制层
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/station")
@Api(tags = "驿站管理")
public class CourierStationController extends BaseController{
    @Resource
    private CourierStationService courierStationService;

    

}