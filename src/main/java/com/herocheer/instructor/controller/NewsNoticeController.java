package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.NewsNotice;
import com.herocheer.instructor.domain.entity.NewsNoticeLog;
import com.herocheer.instructor.domain.vo.NewsQueryVo;
import com.herocheer.instructor.service.NewsNoticeService;
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
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @ApiOperation("新闻列表查询")
    public ResponseResult<Page<NewsNotice>> queryPageList(@RequestBody NewsQueryVo newsQueryVo){
        Page<NewsNotice> page = newsNoticeService.queryPageList(newsQueryVo);
        return ResponseResult.ok().setData(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询新闻活动")
    public ResponseResult<NewsNotice> get(@ApiParam("新闻id") @RequestParam Long id){

        return ResponseResult.ok(newsNoticeService.get(id));
    }


    @PostMapping("/add")
    @ApiOperation("新增新闻活动")
    public ResponseResult add(@RequestBody NewsNotice newsNotice){
        return ResponseResult.isSuccess(newsNoticeService.addNews(newsNotice));
    }

    @PostMapping("/update")
    @ApiOperation("编辑新闻活动")
    public ResponseResult update(@RequestBody NewsNotice newsNotice){
        newsNoticeService.updateNewsNotice(newsNotice);
        return ResponseResult.ok();
    }

    @GetMapping("/cancelNews")
    @ApiOperation("取消新闻")
    public ResponseResult cancelNews(@ApiParam("新闻id") @RequestParam Long id,
                                     @ApiParam("上下架状态0上架 1下架") @RequestParam Integer isPublic){
        NewsNotice newsNotice = new NewsNotice();
        newsNotice.setId(id);
        newsNotice.setIsPublic(isPublic);
        return ResponseResult.isSuccess(newsNoticeService.update(newsNotice));
    }


    @DeleteMapping("/delete")
    @ApiOperation("删除新闻活动")
    public ResponseResult delete(@ApiParam("新闻id") @RequestParam Long id){
        return ResponseResult.isSuccess(newsNoticeService.delete(id));
    }

    @GetMapping("/approval")
    @ApiOperation("新闻活动审批")
    public ResponseResult approval(@ApiParam("新闻活动id") @RequestParam Long id,
                                   @ApiParam("审核状态 1通过2驳回3撤回") @RequestParam int auditState,
                                   @ApiParam("审核意见") @RequestParam(required = false) String auditIdea,
                                   HttpServletRequest request){
        newsNoticeService.approval(id,auditState,auditIdea,getCurUserId(request));
        return ResponseResult.ok();
    }

    @GetMapping("/getApprovalLog")
    @ApiOperation("新闻活动审批日志列表")
    public ResponseResult<List<NewsNoticeLog>> getApprovalLog(@ApiParam("新闻活动id") @RequestParam Long newsId){
        List<NewsNoticeLog> logs = newsNoticeService.getApprovalLog(newsId);
        return ResponseResult.ok(logs);
    }

}