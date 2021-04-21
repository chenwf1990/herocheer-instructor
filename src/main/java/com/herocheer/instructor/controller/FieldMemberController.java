package com.herocheer.instructor.controller;

import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.FieldMember;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.service.FieldMemberService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 场地采集人员(FieldMember)表控制层
 * @date 2021-04-21 16:01:52
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@RestController
@RequestMapping("/field")
@Api(tags = "场地采集人员")
public class FieldMemberController extends BaseController {
    @Autowired
    private FieldMemberService fieldMemberService;

    @Autowired
    private RedisClient redisClient;


    @GetMapping("/member")
    @ApiOperation("采集人员")
    public ResponseResult<Boolean> fetchUserById(HttpServletRequest request){
        // 获取当前用户信息
        String userInfo = redisClient.get(getCurTokenId(request));
        UserInfoVo infoVo = JSONObject.parseObject(userInfo,UserInfoVo.class);
        log.debug("当前用户信息：{}",infoVo);
        log.debug("当前用户信息ID：{}",infoVo.getId());
        Map paramMap = new HashMap<String,Object>();
        paramMap.put("phone",infoVo.getPhone());
//        paramMap.put("phone","tHw/l6vrLXJ74H+GRyupCQ==");
        log.debug("当前用户信息手机:{}",infoVo.getPhone());
        List<FieldMember> fieldMemberes=  fieldMemberService.findByLimit(paramMap);
        if(CollectionUtils.isEmpty(fieldMemberes)){
            return ResponseResult.ok(Boolean.FALSE);
        }
        return ResponseResult.ok(Boolean.TRUE);
    }
}