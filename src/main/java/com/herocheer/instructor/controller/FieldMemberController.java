package com.herocheer.instructor.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.FieldMember;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.enums.InsuranceConst;
import com.herocheer.instructor.service.FieldMemberService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.utils.AesUtil;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 场地采集人员表(FieldMember)表控制层
 * @date 2021-06-01 17:54:18
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

    @Autowired
    private UserService userService;


    @GetMapping("/member")
    @ApiOperation("采集人员")
    public ResponseResult<Boolean> fetchUserById(HttpServletRequest request){
        // 获取当前用户信息
        String userInfo = redisClient.get(getCurTokenId(request));
        UserInfoVo infoVo = JSONObject.parseObject(userInfo,UserInfoVo.class);
        log.debug("当前用户信息：{}",infoVo);
        log.debug("当前用户信息ID：{}",infoVo.getId());

        // 身份证号为空，说明未登入
        User user  = userService.findUserByOpenId(infoVo.getOtherId(),null);
        if(!StringUtils.hasText(user.getCertificateNo())){
            return ResponseResult.ok(Boolean.FALSE);
        }

        // 去I健身获取采集人id
        String certificateNo = AesUtil.decrypt(user.getCertificateNo());
        String sign = DigestUtils.md5DigestAsHex((certificateNo + InsuranceConst.KEY).getBytes());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sign", sign);
        paramMap.put("certificateNo", certificateNo);
        String result= HttpUtil.post(InsuranceConst.BASE_URL+"/familyManage/members", paramMap);
        JSONObject JSONObj = JSONObject.parseObject(result);
        if(JSONObj == null || JSONObj.getInteger("code") != 200){
            log.error("请求i健身用户信息失败:{}",JSONObj);
            throw new CommonException("请求i健身用户信息失败");
        }

        // 获取信息为空
        JSONArray array = JSONArray.parseArray(JSONObj.getString("result"));
        if(ObjectUtils.isEmpty(array)){
            return ResponseResult.ok(Boolean.FALSE);
        }

        // 多个值中取一个值
        JSONObject object = null;
        for (int i = 0; i < array.size(); i++) {
            if(certificateNo.equals(array.getJSONObject(i).getString("certificateNo"))){
                object = array.getJSONObject(i);
                break;
            }
        }

        // 用户没有采集人ID
        String  othId = object.getString("othId");
        if(!StringUtils.hasText(othId)){
            return ResponseResult.ok(Boolean.FALSE);
        }

        Map param = new HashMap<String,Object>();
        param.put("memberId",othId);
        log.debug("当前用户编号:{}",othId);
        List<FieldMember> fieldMemberes=  fieldMemberService.findByLimit(param);
        if(CollectionUtils.isEmpty(fieldMemberes)){
            return ResponseResult.ok(Boolean.FALSE);
        }
        return ResponseResult.ok(Boolean.TRUE);
    }
}