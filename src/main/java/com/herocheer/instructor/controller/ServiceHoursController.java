package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.ServiceHours;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.service.ServiceHoursService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author makejava
 * @desc  驿站服务时段 (ServiceHours)表控制层
 * @date 2021-01-05 11:32:26
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/service/hours")
@Api(tags = "驿站服务时间段管理")
public class ServiceHoursController extends BaseController{
    @Resource
    private ServiceHoursService serviceHoursService;

    @PostMapping("/queryPageList")
    @ApiOperation("查询驿站服务时间段列表")
    public ResponseResult<Page<ServiceHours>> queryPageList(@RequestBody ServiceHoursQueryVo serviceHoursQueryVo){
        Page<ServiceHours> serviceHours= serviceHoursService.queryPageList(serviceHoursQueryVo);
        return ResponseResult.ok().setData(serviceHours);
    }

    @PostMapping("/add")
    @ApiOperation("新增驿站服务时段")
    public ResponseResult add(@RequestBody ServiceHours serviceHours){
        int count=serviceHoursService.insert(serviceHours);
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/update")
    @ApiOperation("更新驿站服务时段")
    public ResponseResult update(@RequestBody ServiceHours serviceHours){
        int count=serviceHoursService.update(serviceHours);
        return ResponseResult.isSuccess(count);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除驿站服务时段")
    public ResponseResult delete(@ApiParam("服务时段Id") @RequestParam Long id){
        return ResponseResult.isSuccess(serviceHoursService.delete(id));
    }

    @GetMapping("/getHoursByStationId")
    @ApiOperation("根据驿站id获取服务时段列表")
    public ResponseResult<List<ServiceHours>> getHoursByStationId(@ApiParam("驿站Id") @RequestParam Long StationId){
        List<ServiceHours> serviceHoursList=serviceHoursService.getHoursByStationId(StationId);
        return ResponseResult.ok().setData(serviceHoursList);
    }
}