package com.herocheer.instructor.controller;

import cn.hutool.core.lang.tree.Tree;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.vo.AreaQueryVo;
import com.herocheer.instructor.domain.vo.AreaTreeVO;
import com.herocheer.instructor.domain.vo.AreaVO;
import com.herocheer.instructor.service.SysAreaService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chenwf
 * @desc  区域管理（省市区街道社区）(SysArea)表控制层
 * @date 2021-01-07 09:50:58
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/area")
@Api(tags = "系统区域")
public class SysAreaController extends BaseController{
    @Resource
    private SysAreaService sysAreaService;

    @PostMapping("/queryPageList")
    @ApiOperation("区域列表")
    public ResponseResult<Page<SysArea>> queryPageList(@RequestBody AreaQueryVo areaQueryVo){
        Page<SysArea> pageList = sysAreaService.queryPageList(areaQueryVo);
        return ResponseResult.ok(pageList);
    }

    @GetMapping("/getAllArea")
    @ApiOperation("区域树(无权限)")
    public ResponseResult<List<Tree<Long>>> getAllArea(@ApiParam("父级pid 厦门市1") @RequestParam(required = false) Long pid){
        List<Tree<Long>> nodeList = sysAreaService.getAllArea(1,pid);
        return ResponseResult.ok(nodeList);
    }


    @GetMapping("/getAllAreaByRole")
    @ApiOperation("区域权限树(权限过滤)")
    public ResponseResult<List<Tree<Long>>> getAllAreaByRole(@ApiParam("父级pid 厦门市1") @RequestParam(required = false) Long pid){
        return ResponseResult.ok(sysAreaService.getAllArea(2,pid));
    }


    /**
     * 通过角色id获取区域
     *
     * @param roleId  角色id
     * @param request 请求
     * @return {@link ResponseResult<AreaTreeVO>}
     */
    @GetMapping("/{roleId:\\w+}")
    @ApiOperation("根据roleId获取区域树")
    public ResponseResult<AreaTreeVO> fetchAreaByRoleId(@ApiParam("角色ID") @PathVariable Long roleId, HttpServletRequest request){
        AreaTreeVO areaTreeVO = AreaTreeVO.builder().build();
        List<Tree<Long>> nodeList = sysAreaService.getAllArea(1);
        areaTreeVO.setTreeNode(nodeList);
        //  选中节点
        List<String> stringList = sysAreaService.findAreaNode(roleId);
        if(CollectionUtils.isEmpty(stringList)){
            areaTreeVO.setSelectedNode(null);
            return ResponseResult.ok(areaTreeVO);
        }
        areaTreeVO.setSelectedNode(String.join(",",stringList));
        return ResponseResult.ok(areaTreeVO);
    }
    /**
     * 根据id获取子层区域
     *
     * @return {@link ResponseResult<List<SysArea>>}
     */
    @PostMapping("/page")
    @ApiOperation("根据id获取子层区域")
    public ResponseResult<Page<SysArea>> fetchAreaById(@ApiParam("区域VO") @RequestBody AreaQueryVo areaQueryVo, HttpServletRequest request){
        return ResponseResult.ok(sysAreaService.findAreaById(areaQueryVo));
    }


    /**
     * 创建区域
     *
     * @param areaVO  VO
     * @param request 请求
     * @return {@link ResponseResult<SysArea>}
     */
    @PostMapping("/area")
    @ApiOperation("添加区域")
    public ResponseResult<SysArea> createArea(@ApiParam("区域VO") @RequestBody AreaVO areaVO, HttpServletRequest request){
        return ResponseResult.ok(sysAreaService.addArea(areaVO));
    }
}