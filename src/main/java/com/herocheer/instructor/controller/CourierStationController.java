package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.vo.CourierStationQueryVo;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.service.CourierStationService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/queryPageList")
    @ApiOperation("驿站列表列表查询")
    @AllowAnonymous
    public ResponseResult<Page<CourierStation>> queryPageList(@RequestBody CourierStationQueryVo courierStationQueryVo){
        Page<CourierStation> page = courierStationService.queryPageList(courierStationQueryVo);
        return ResponseResult.ok(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询驿站")
    @AllowAnonymous
    public ResponseResult<CourierStation> get(@ApiParam("驿站id") @RequestParam Long id){

        return ResponseResult.ok(courierStationService.get(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增驿站")
    public ResponseResult add(@RequestBody CourierStation courierStation){
        return ResponseResult.isSuccess(courierStationService.addCourierStation(courierStation));
    }

    @PostMapping("/update")
    @ApiOperation("编辑驿站")
    public ResponseResult update(@RequestBody CourierStation courierStation){
        return ResponseResult.isSuccess(courierStationService.update(courierStation));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除驿站")
    public ResponseResult delete(@ApiParam("驿站id") @RequestParam Long id){
        return ResponseResult.isSuccess(courierStationService.delete(id));
    }
}