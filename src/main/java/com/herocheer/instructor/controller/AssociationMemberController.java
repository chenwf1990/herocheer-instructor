package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.AssociationManage;
import com.herocheer.instructor.domain.entity.AssociationMember;
import com.herocheer.instructor.domain.vo.AssociationDictVo;
import com.herocheer.instructor.domain.vo.AssociationManageQueryVo;
import com.herocheer.instructor.domain.vo.AssociationManageVo;
import com.herocheer.instructor.domain.vo.AssociationMemberQueryVo;
import com.herocheer.instructor.service.AssociationMemberService;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author makejava
 * @desc  协会成员(AssociationMember)表控制层
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/association/member")
@Api(tags = "协会成员管理")
public class AssociationMemberController extends BaseController{
    @Resource
    private AssociationMemberService associationMemberService;

    @PostMapping("/queryPage")
    @ApiOperation("分页查询协会成员信息")
    public ResponseResult<Page<AssociationMember>> queryPage(@RequestBody AssociationMemberQueryVo queryVo){
        Page<AssociationMember> page = associationMemberService.queryPage(queryVo);
        return ResponseResult.ok(page);
    }

    @GetMapping("/get")
    @ApiOperation("获取协会成员详情")
    public ResponseResult<AssociationMember> getAssociation(@ApiParam("协会成员id") @RequestParam Long id){
        AssociationMember associationMember = associationMemberService.get(id);
        return ResponseResult.ok(associationMember);
    }

    @PostMapping("/add")
    @ApiOperation("新增协会成员")
    public ResponseResult add(@RequestBody AssociationMember associationMember){
        Integer count=associationMemberService.addMember(associationMember);
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/update")
    @ApiOperation("修改协会成员信息")
    public ResponseResult update(@RequestBody AssociationMember associationMember){
        Integer count=associationMemberService.updateMember(associationMember);
        return ResponseResult.isSuccess(count);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除协会成员")
    public ResponseResult delete(@ApiParam("协会id") @RequestParam Long id){
        return ResponseResult.isSuccess(associationMemberService.delMember(id));
    }

    @GetMapping("/templateExport")
    @ApiOperation("模板导出")
    @AllowAnonymous
    public ResponseResult templateExport(@ApiParam("协会id") @RequestParam Long associationId,
                                         HttpServletResponse response){
        associationMemberService.templateExport(associationId,response);
        return ResponseResult.ok();
    }

    @PostMapping("/import")
    @ApiOperation("协会成员导入")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "file", value = "文件对象", required = true, dataType = "__file"),
            @ApiImplicitParam(name = "associationId", value = "协会id",dataType = "long",paramType = "query")
    })
    public ResponseResult associationMemberImport(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request){
        String associationId = request.getParameter("associationId");
        String filename = multipartFile.getOriginalFilename().toLowerCase();
        if(!filename.contains(".xls") && !filename.contains(".xlsx")){
            throw new CommonException("模板错误，数据导入失败");
        }
        associationMemberService.associationMemberImport(Long.valueOf(associationId),multipartFile);
        return ResponseResult.ok();
    }

}