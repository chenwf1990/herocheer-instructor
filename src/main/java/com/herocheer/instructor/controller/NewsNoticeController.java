package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.NewsNotice;
import com.herocheer.instructor.domain.vo.InstructorQueryVo;
import com.herocheer.instructor.service.NewsNoticeService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenwf
 * @desc  新闻公告(NewsNotice)表控制层
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/news")
@Api(tags = "新闻公告")
public class NewsNoticeController extends BaseController{
    @Resource
    private NewsNoticeService newsNoticeService;


    @PostMapping("/queryPageList")
    @ApiOperation("指导员列表查询")
    public ResponseResult queryPageList(@RequestBody InstructorQueryVo instructorQueryVo){
        Page<NewsNotice> page = newsNoticeService.queryPageList(instructorQueryVo);
        return ResponseResult.ok().setData(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询新闻活动")
    public ResponseResult get(@ApiParam("新闻id") @RequestParam Long id){

        return ResponseResult.ok().setData(newsNoticeService.get(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增新闻活动")
    public ResponseResult add(@RequestBody NewsNotice newsNotice){
        return ResponseResult.isSuccess(newsNoticeService.insert(newsNotice));
    }

    @PostMapping("/update")
    @ApiOperation("编辑新闻活动")
    public ResponseResult update(@RequestBody NewsNotice newsNotice){
        return ResponseResult.isSuccess(newsNoticeService.update(newsNotice));
    }


    @DeleteMapping("/delete")
    @ApiOperation("删除新闻活动")
    public ResponseResult delete(@ApiParam("新闻id") @RequestParam Long id){
        return ResponseResult.isSuccess(newsNoticeService.delete(id));
    }

    @GetMapping("/approval")
    @ApiOperation("新闻活动审批")
    public ResponseResult approval(@ApiParam("指导员id") @RequestParam Long id,
                                   @ApiParam("审核状态 0待审核1审核通过2审核驳回") @RequestParam int auditState,
                                   @ApiParam("审核意见") @RequestParam String auditIdea){
//        instructorService.approval(id,auditState,auditIdea);
        return ResponseResult.ok();
    }

}