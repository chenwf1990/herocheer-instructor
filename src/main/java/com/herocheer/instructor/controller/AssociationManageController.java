package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import com.herocheer.instructor.domain.entity.AssociationManage;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.ActivityRecruitInfoVo;
import com.herocheer.instructor.domain.vo.AssociationDictVo;
import com.herocheer.instructor.domain.vo.AssociationManageQueryVo;
import com.herocheer.instructor.domain.vo.AssociationManageVo;
import com.herocheer.instructor.service.AssociationManageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author linjf
 * @desc  协会管理(AssociationManage)表控制层
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/association/manage")
@Api(tags = "协会管理")
public class AssociationManageController extends BaseController{

    @Resource
    private AssociationManageService associationManageService;

    @PostMapping("/queryPage")
    @ApiOperation("分页查询协会信息")
    public ResponseResult<Page<AssociationManageVo>> queryPage(@RequestBody AssociationManageQueryVo queryVo){
        Page<AssociationManageVo> page = associationManageService.queryPage(queryVo);
        return ResponseResult.ok(page);
    }

    @GetMapping("/get")
    @ApiOperation("获取协会详情")
    public ResponseResult<AssociationManage> getAssociation(@ApiParam("协会id") @RequestParam Long id){
        AssociationManage associationManage = associationManageService.getAssociation(id);
        return ResponseResult.ok(associationManage);
    }

    @PostMapping("/add")
    @ApiOperation("新增协会")
    public ResponseResult add(@RequestBody AssociationManage associationManage){
        Integer count=associationManageService.addAssociation(associationManage);
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/update")
    @ApiOperation("修改协会信息")
    public ResponseResult update(@RequestBody AssociationManage associationManage){
        Integer count=associationManageService.updateAssociation(associationManage);
        return ResponseResult.isSuccess(count);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除协会信息")
    public ResponseResult delete(@ApiParam("协会id") @RequestParam Long id){
        return ResponseResult.isSuccess(associationManageService.delAssociation(id));
    }

    @PostMapping("/dict")
    @ApiOperation("协会字典类型")
    public ResponseResult<List<AssociationDictVo>> dict(){
        List<AssociationDictVo> list=associationManageService.getAssociationDict();
        return ResponseResult.ok(list);
    }
}