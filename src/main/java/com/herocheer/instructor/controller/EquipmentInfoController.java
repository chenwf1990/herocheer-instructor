package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.BrandInfo;
import com.herocheer.instructor.domain.entity.EquipmentInfo;
import com.herocheer.instructor.domain.vo.EquipmentInfoQueryVo;
import com.herocheer.instructor.domain.vo.EquipmentInfoStockVo;
import com.herocheer.instructor.service.EquipmentInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

/**
 * @author makejava
 * @desc  器材信息 (EquipmentInfo)表控制层
 * @date 2021-04-19 17:18:25
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/equipment/info")
@Api(tags = "器材管理")
public class EquipmentInfoController extends BaseController{
    @Resource
    private EquipmentInfoService equipmentInfoService;

    @PostMapping("/queryPage")
    @ApiOperation("分页查询器材")
    public ResponseResult<Page<EquipmentInfo>> queryPage(@RequestBody EquipmentInfoQueryVo queryVo){
        Page<EquipmentInfo> page = equipmentInfoService.queryPage(queryVo);
        return ResponseResult.ok(page);
    }

    @PostMapping("/queryStockPage")
    @ApiOperation("库存分页查询")
    public ResponseResult<Page<EquipmentInfoStockVo>> queryStockPage(@RequestBody EquipmentInfoQueryVo queryVo){
        Page<EquipmentInfoStockVo> page = equipmentInfoService.queryStockPage(queryVo);
        return ResponseResult.ok(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增器材信息")
    public ResponseResult add(@RequestBody EquipmentInfo equipmentInfo){
        Integer count=equipmentInfoService.addEquipment(equipmentInfo);
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/update")
    @ApiOperation("修改器材信息")
    public ResponseResult update(@RequestBody EquipmentInfo equipmentInfo){
        Integer count=equipmentInfoService.updateEquipment(equipmentInfo);
        return ResponseResult.isSuccess(count);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除器材信息")
    public ResponseResult delete(@ApiParam("器材id") @RequestParam Long id){
        return ResponseResult.isSuccess(equipmentInfoService.deleteEquipment(id));
    }
}