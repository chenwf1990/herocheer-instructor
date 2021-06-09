package com.herocheer.instructor.controller;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.ReportClicks;
import com.herocheer.instructor.domain.vo.ReportClicksStatisVO;
import com.herocheer.instructor.domain.vo.ReportClicksVO;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.service.ReportClicksService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author gaorh
 * @desc 点击量(ReportClicks)表控制层
 * @date 2021-06-08 14:41:53
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/report")
@Api(tags = "项目点击量")
public class ReportClicksController extends BaseController {
    @Resource
    private ReportClicksService reportClicksService;

    @Autowired
    private RedisClient redisClient;

    @PostMapping("/clicks/info")
    @ApiOperation("新增点击量")
    public ResponseResult<ReportClicks> createDiary(@ApiParam("点击量信息") @RequestBody ReportClicks reportClicks, HttpServletRequest request){
        // 获取当前用户信息
        String userInfo = redisClient.get(getCurTokenId(request));
        UserInfoVo infoVo = JSONObject.parseObject(userInfo,UserInfoVo.class);
        return ResponseResult.ok(reportClicksService.addReportClicks(reportClicks,infoVo));
    }

    /**
     * 获取点击量列表
     *
     * @param request        请求
     * @param reportClicksVO 报告点击签证官
     * @return {@link ResponseResult<Page<ReportClicksStatisVO>>}
     */
    @PostMapping("/clicks/page")
    @ApiOperation("点击量列表")
    public ResponseResult<Page<ReportClicksStatisVO>> fetchClicksByPage(@ApiParam("点击量信息") @RequestBody ReportClicksVO reportClicksVO, HttpServletRequest request){
        // 新增工作日志
        return ResponseResult.ok(reportClicksService.findReportClicksByPage(reportClicksVO));
    }
}
