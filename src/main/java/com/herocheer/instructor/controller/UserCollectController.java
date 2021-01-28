package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.vo.UserCollectVo;
import com.herocheer.instructor.service.UserCollectService;
import com.herocheer.instructor.service.WechatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author chenwf
 * @desc  用户收藏(UserCollect)表控制层
 * @date 2021-01-26 11:24:26
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/collect")
@Api(tags = "用户收藏")
public class UserCollectController extends BaseController{
    @Resource
    private UserCollectService userCollectService;
    @Resource
    private WechatService wecharService;


    @PostMapping(value = "/userCollect")
    @ApiOperation(value = "收藏/取消收藏")
    public ResponseResult userCollect(@RequestBody UserCollectVo userCollectVo,
                                      HttpServletRequest request){
        userCollectVo.setUserId(getCurUserId(request));
        return ResponseResult.isSuccess(userCollectService.userCollect(userCollectVo));
    }

    @GetMapping(value = "/getAccessToken")
    @ApiOperation(value = "获取微信token")
    public ResponseResult getAccessToken(){

        return ResponseResult.ok(wecharService.getRedisAccessToken());
    }
}