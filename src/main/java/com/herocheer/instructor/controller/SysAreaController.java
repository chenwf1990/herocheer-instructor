package com.herocheer.instructor.controller;

import cn.hutool.core.lang.tree.Tree;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.vo.AreaQueryVo;
import com.herocheer.instructor.service.SysAreaService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenwf
 * @desc  区域管理（省市区街道社区）(SysArea)表控制层
 * @date 2021-01-07 09:50:58
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/area")
@Api(tags = "区域管理")
public class SysAreaController extends BaseController{
    @Resource
    private SysAreaService sysAreaService;

    @GetMapping("/queryPageList")
    @ApiOperation("区域列表查询")
    @AllowAnonymous
    public ResponseResult<Page<SysArea>> queryPageList(@RequestBody AreaQueryVo areaQueryVo){
        Page<SysArea> pageList = sysAreaService.queryPageList(areaQueryVo);
        return ResponseResult.ok(pageList);
    }

    @GetMapping("/getAllArea")
    @ApiOperation("获取区域树(无权限)")
    @AllowAnonymous
    public ResponseResult<List<Tree<Long>>> getAllArea(){
        List<Tree<Long>> nodeList = sysAreaService.getAllArea(1);
        return ResponseResult.ok(nodeList);
    }


    @GetMapping("/getAllAreaByRole")
    @ApiOperation("获取区域树(权限过滤)")
    @AllowAnonymous
    public ResponseResult<List<Tree<Long>>> getAllAreaByRole(){

        return ResponseResult.ok(sysAreaService.getAllArea(2));
    }
}