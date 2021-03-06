package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.domain.entity.FitnessVideo;
import com.herocheer.instructor.domain.vo.FitnessVideoVo;
import com.herocheer.instructor.domain.vo.VideoQueryVo;
import com.herocheer.instructor.service.FitnessVideoService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author chenwf
 * @desc  健身视频管理(FitnessVideo)表控制层
 * @date 2021-03-17 09:46:10
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/fitness/video")
@Api(tags = "健身视频管理")
public class FitnessVideoController extends BaseController{
    @Resource
    private FitnessVideoService fitnessVideoService;
    final String videoDefaultUrl = "/instructor/video/";

    @PostMapping("/queryPageList")
    @ApiOperation("健身视频列表查询")
    public ResponseResult<Page<FitnessVideoVo>> queryPageList(@RequestBody VideoQueryVo videoQueryVo,
                                                              HttpServletRequest request){
        UserEntity user = getUser(request);
        videoQueryVo.setOpenId(user.getOtherId());
        Page<FitnessVideoVo> page = fitnessVideoService.queryPageList(videoQueryVo);
        return ResponseResult.ok(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询健身视频")
    public ResponseResult<FitnessVideo> get(@ApiParam("健身视频id") @RequestParam Long id){

        return ResponseResult.ok(fitnessVideoService.get(id));
    }


    @DeleteMapping("/delete")
    @ApiOperation("删除健身视频")
    public ResponseResult delete(@ApiParam("健身视频id") @RequestParam Long id){

        return ResponseResult.isSuccess(fitnessVideoService.delete(id));
    }

    @PostMapping("/update")
    @ApiOperation("更新健身视频")
    public ResponseResult get(@RequestBody FitnessVideo fitnessVideo){
        String videoUrl = setVideoUrl(fitnessVideo.getVideoUrl());
        fitnessVideo.setVideoUrl(videoUrl);
        return ResponseResult.ok(fitnessVideoService.update(fitnessVideo));
    }

    private String setVideoUrl(String videoUrl) {
        if(StringUtils.isNotEmpty(videoUrl)){
            if(videoUrl.toLowerCase().startsWith("http")){
                return videoUrl;
            }
            if(videoUrl.startsWith(videoDefaultUrl)){
                return videoUrl;
            }
            videoUrl = videoDefaultUrl + videoUrl;
        }
        return videoUrl;
    }

    @GetMapping("/updateState")
    @ApiOperation("启用/禁用")
    public ResponseResult updateState(@ApiParam("健身视频id") @RequestParam Long id,
                                      @ApiParam("状态 0上架 1下架") @RequestParam int state){
        FitnessVideo fitnessVideo = new FitnessVideo();
        fitnessVideo.setId(id);
        fitnessVideo.setState(state);
        return ResponseResult.ok(fitnessVideoService.update(fitnessVideo));
    }

    @PostMapping("/add")
    @ApiOperation("新增健身视频")
    public ResponseResult add(@RequestBody FitnessVideo fitnessVideo){
        String videoUrl = setVideoUrl(fitnessVideo.getVideoUrl());
        fitnessVideo.setVideoUrl(videoUrl);
        return ResponseResult.isSuccess(fitnessVideoService.insert(fitnessVideo));
    }

    @GetMapping("/addBrowseNum")
    @ApiOperation("添加浏览数量")
    public ResponseResult addBrowseNum(@ApiParam("健身视频id") @RequestParam Long id){
        return ResponseResult.isSuccess(fitnessVideoService.addBrowseNum(id));
    }

}