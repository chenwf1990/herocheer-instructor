package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.AssociationMember;
import com.herocheer.instructor.domain.entity.BrandInfo;
import com.herocheer.instructor.domain.entity.EquipmentBorrow;
import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.instructor.domain.entity.EquipmentInfo;
import com.herocheer.instructor.domain.entity.EquipmentRemand;
import com.herocheer.instructor.domain.vo.EquipmentBorrowQueryVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowSaveVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowVo;
import com.herocheer.instructor.domain.vo.EquipmentDamageVo;
import com.herocheer.instructor.domain.vo.EquipmentRemandVo;
import com.herocheer.instructor.service.EquipmentBorrowService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author makejava
 * @desc  器材借用 (EquipmentBorrow)表控制层
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/equipment")
@Api(tags = "器材借用")
public class EquipmentBorrowController extends BaseController{
    @Resource
    private EquipmentBorrowService equipmentBorrowService;

    @PostMapping("/queryPage")
    @ApiOperation("器材借用查询")
    public ResponseResult<Page<EquipmentBorrow>> queryPage(@RequestBody EquipmentBorrowQueryVo queryVo,HttpServletRequest request){
        Page<EquipmentBorrow> page = equipmentBorrowService.queryPage(queryVo,getCurUserId(request));
        return ResponseResult.ok(page);
    }


    @PostMapping("/borrow/add")
    @ApiOperation("新增借用")
    public ResponseResult addBorrow(@RequestBody EquipmentBorrowSaveVo equipmentInfo, HttpServletRequest request){
        Integer count=equipmentBorrowService.addBorrow(equipmentInfo,getCurUserId(request));
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/borrow/confirm")
    @ApiOperation("值班人员确认借用")
    public ResponseResult confirmBorrow(@RequestBody List<EquipmentBorrowDetails> details, HttpServletRequest request){
        Integer count=equipmentBorrowService.confirmBorrow(details,getCurUserId(request));
        return ResponseResult.isSuccess(count);
    }

    @GetMapping("/user/receipt")
    @ApiOperation("用户确认签收")
    public ResponseResult userReceipt(@ApiParam("借用id") @RequestParam Long id){
        Integer count=equipmentBorrowService.userReceipt(id);
        return ResponseResult.isSuccess(count);
    }

    @GetMapping("/borrow/overrule")
    @ApiOperation("借用驳回")
    public ResponseResult overrule(@ApiParam("借用id") @RequestParam Long id){
        Integer count=equipmentBorrowService.overrule(id);
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/remand/apply")
    @ApiOperation("申请归还")
    public ResponseResult applyRemand(@RequestBody List<EquipmentRemand> remand){
        Integer count=equipmentBorrowService.applyRemand(remand);
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/remand/confirm")
    @ApiOperation("值班人员确认归还")
    public ResponseResult confirmRemand(@RequestBody List<EquipmentRemand> remand){
        Integer count=equipmentBorrowService.confirmRemand(remand);
        return ResponseResult.isSuccess(count);
    }

    @GetMapping("/remand/apply/list")
    @ApiOperation("查询申请归还的器材")
    public ResponseResult<List<EquipmentRemandVo>> remandList(@ApiParam("借用id") @RequestParam Long id){
        List<EquipmentRemandVo> list = equipmentBorrowService.remandList(id);
        return ResponseResult.ok(list);
    }

    @GetMapping("/remand/msg")
    @ApiOperation("归还提示")
    public ResponseResult<String> getRemandMsg(@ApiParam("借用id") @RequestParam Long id){
        String msg = equipmentBorrowService.getRemandMsg(id);
        return ResponseResult.ok(msg);
    }

    @GetMapping("/remand/user/confirm")
    @ApiOperation("用户确认归还")
    public ResponseResult userConfirmRemand(@ApiParam("借用id") @RequestParam Long id){
        Integer count=equipmentBorrowService.userConfirmRemand(id);
        return ResponseResult.isSuccess(count);
    }

    @GetMapping("/get")
    @ApiOperation("获取借用详情")
    public ResponseResult<EquipmentBorrowVo> getEquipmentBorrow(@ApiParam("借用id") @RequestParam Long id){
        EquipmentBorrowVo vo = equipmentBorrowService.getEquipmentBorrow(id);
        return ResponseResult.ok(vo);
    }

    @GetMapping("/user/borrow/count")
    @ApiOperation("获取用户进行中单据的统计数")
    public ResponseResult<Integer> getCountByUserId(HttpServletRequest request){
        Integer count = equipmentBorrowService.getCountByUserId(getCurUserId(request));
        return ResponseResult.ok(count);
    }

    @PostMapping("/add/damage")
    @ApiOperation("新增报废")
    public ResponseResult andDamage(@RequestBody EquipmentDamageVo vo){
        Integer count=equipmentBorrowService.andDamage(vo);
        return ResponseResult.isSuccess(count);
    }

    @GetMapping("/get/damage")
    @ApiOperation("获取报废详情")
    public ResponseResult<List<EquipmentDamageVo>> getDamage(@ApiParam("借用id") @RequestParam Long id){
        List<EquipmentDamageVo> list= equipmentBorrowService.getDamage(id);
        return ResponseResult.ok(list);
    }
}