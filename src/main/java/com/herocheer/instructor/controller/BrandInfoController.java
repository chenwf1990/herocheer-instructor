package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.AssociationMember;
import com.herocheer.instructor.domain.entity.BrandInfo;
import com.herocheer.instructor.domain.vo.MatchSignRecordVo;
import com.herocheer.instructor.service.BrandInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author makejava
 * @desc  品牌管理 (BrandInfo)表控制层
 * @date 2021-04-19 15:52:24
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/brand")
@Api(tags = "品牌管理")
public class BrandInfoController extends BaseController{
    @Resource
    private BrandInfoService brandInfoService;

    @GetMapping("/queryPage")
    @ApiOperation("分页查询品牌")
    public ResponseResult<Page<BrandInfo>> queryPage(@ApiParam("品牌名") @RequestParam String brandName,
                                                                @ApiParam("页数") @RequestParam Integer pageSize,
                                                                @ApiParam("页码") @RequestParam Integer pageNo){
        Page<BrandInfo> page = brandInfoService.queryPage(brandName,pageNo,pageSize);
        return ResponseResult.ok(page);
    }

    @PostMapping("/add")
    @ApiOperation("新增品牌")
    public ResponseResult add(@RequestBody BrandInfo brandInfo){
        Integer count=brandInfoService.addBrand(brandInfo);
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/update")
    @ApiOperation("修改品牌信息")
    public ResponseResult update(@RequestBody BrandInfo brandInfo){
        Integer count=brandInfoService.updateBrand(brandInfo);
        return ResponseResult.isSuccess(count);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除品牌信息")
    public ResponseResult delete(@ApiParam("品牌id") @RequestParam Long id){
        return ResponseResult.isSuccess(brandInfoService.deleteBrand(id));
    }

    @GetMapping("/queryList")
    @ApiOperation("不分页查询品牌")
    public ResponseResult<List<BrandInfo>> queryList(){
        List<BrandInfo> page = brandInfoService.queryList();
        return ResponseResult.ok(page);
    }

}