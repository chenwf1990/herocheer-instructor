package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.Banner;
import com.herocheer.instructor.domain.vo.BannerQueryVo;
import com.herocheer.instructor.service.BannerService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenwf
 * @desc  banner管理(Banner)表控制层
 * @date 2021-03-17 09:43:08
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/banner")
@Api(tags = "banner管理")
public class BannerController extends BaseController{
    @Resource
    private BannerService bannerService;

    @PostMapping("/queryPageList")
    @ApiOperation("banner列表查询")
    public ResponseResult<Page<Banner>> queryPageList(@RequestBody BannerQueryVo bannerQueryVo){
        Page<Banner> page = bannerService.queryPageList(bannerQueryVo);
        return ResponseResult.ok(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询banner")
    public ResponseResult<Banner> get(@ApiParam("bannerId") @RequestParam Long id){

        return ResponseResult.ok(bannerService.get(id));
    }


    @DeleteMapping("/delete")
    @ApiOperation("删除banner")
    public ResponseResult delete(@ApiParam("bannerId") @RequestParam Long id){

        return ResponseResult.isSuccess(bannerService.delete(id));
    }

    @GetMapping("/update")
    @ApiOperation("更新banner")
    public ResponseResult get(@RequestBody Banner banner){

        return ResponseResult.ok(bannerService.update(banner));
    }

    @GetMapping("/updateState")
    @ApiOperation("上架/下架")
    public ResponseResult updateState(@ApiParam("bannerId") @RequestParam Long id,
                                      @ApiParam("状态 0上架 1下架") @RequestParam int isPublic){
        Banner banner = new Banner();
        banner.setId(id);
        banner.setIsPublic(isPublic);
        return ResponseResult.ok(bannerService.update(banner));
    }

    @PostMapping("/add")
    @ApiOperation("新增banner")
    public ResponseResult add(@RequestBody Banner banner){
        return ResponseResult.isSuccess(bannerService.insert(banner));
    }
}