package com.herocheer.instructor.controller;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
import com.herocheer.instructor.enums.InsuranceConst;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.utils.AesUtil;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gaorh
 * @desc  保险信息
 * @date 2021-02-23 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/insure")
@Api(tags = "保险信息")
@Validated
@Slf4j
public class InsuranceController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/certificate")
    @ApiOperation("当前用户的身份证号码")
    public ResponseResult<String> fecthInsuranceInfoByCorrentUser(HttpServletRequest request){
        UserEntity correntUser = getUser(request);
        User user =  userService.findUserByOpenId(correntUser.getOtherId());

        if(ObjectUtils.isEmpty(user) || StringUtils.isEmpty(user.getCertificateNo())){
            throw new CommonException("您未购买保险,请返回首页购买保险");
        }
        return ResponseResult.ok(user.getCertificateNo());
    }

    /**
     * 我的保单
     *
     * @param request    请求
     * @param WeChatUser 我们聊天用户
     * @return {@link ResponseResult<JSONArray>}
     */
    @PostMapping("/certificateNo")
    @ApiOperation("我的保单")
    public ResponseResult<JSONArray> fecthInsuranceInfoByCertificateNo(@ApiParam("身份证号") @RequestBody WeChatUserVO WeChatUser, HttpServletRequest request){
        String certificateNo = AesUtil.decrypt(WeChatUser.getCertificateNo());
        if(StringUtils.isEmpty(certificateNo)){
            throw new CommonException("保险身份证号不能为空");
        }
        String sign = DigestUtils.md5DigestAsHex((certificateNo + InsuranceConst.KEY).getBytes());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sign", sign);
        paramMap.put("certificateNo", certificateNo);
        log.debug("请求保险信息参数：{}",paramMap);
        log.debug("请求保险信息地址：{}",InsuranceConst.BASE_URL+"/insurance/listInsurance");
        String result= HttpUtil.post(InsuranceConst.BASE_URL+"/insurance/listInsurance", paramMap);

        JSONObject JSONObj = JSONObject.parseObject(result);
        if(JSONObj.getInteger("code") != 200){
            log.error("请求保险信息详情失败:{}",JSONObj);
            throw new CommonException("请求保险信息详情失败");
        }

        // 保险状态情况
        JSONArray array = JSONArray.parseArray(JSONObj.getString("result"));
        // 4-已生效
        JSONArray arr4 = new JSONArray();
        // 5-已过期
        JSONArray arr5 = new JSONArray();

        for(int i=0; i < array.size(); i++){
            if(array.getJSONObject(i).getString("status").equals("4")){
                arr4.add(array.getJSONObject(i));
            }
            if(array.getJSONObject(i).getString("status").equals("5")){
                arr5.add(array.getJSONObject(i));
            }
        }
        if(arr4.size() > 0){
            return ResponseResult.ok(arr4);
        }
        if(arr5.size() > 0){
            throw new CommonException("您购买的保险已过期，请前往i健身首页进行续保，谢谢！");
        }
        throw new CommonException("您未购买保险，请前往i健身首页购买保险");
    }

    /**
     * 保险列表
     *
     * @param request    请求
     * @param pageNo       页面
     * @param pageSize   页面大小
     * @param idNo       id
     * @param name       名字
     * @param insureType 保险类型
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return {@link ResponseResult<JSONObject>}
     */
    @GetMapping("/page")
    @ApiOperation("保险列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "idNo", value = "身份证号",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "name", value = "姓名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "insureType", value = "保险类型：1-购买保险，2-上传保单",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "保险生效时间",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "保险结束时间",dataType = "String",paramType = "query")
    })
    @AllowAnonymous
    public ResponseResult<JSONObject> fecthInsuranceInfoByPage(@NotBlank(message = "签名不能为空") String pageNo,
                                                              @NotBlank(message = "页大小不能为空") String pageSize,
                                                              String idNo, String name,String insureType,String startTime,String endTime,HttpServletRequest request){


        String plain = "";
        if(StringUtils.isNotBlank(name)){
            plain += name;
        }
        if(StringUtils.isNotBlank(idNo)){
            plain += idNo;
        }
        if(StringUtils.isNotBlank(insureType)){
            plain += insureType;
        }
        String sign = DigestUtils.md5DigestAsHex((plain + InsuranceConst.KEY).getBytes());

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sign", sign);
        paramMap.put("page", pageNo);
        paramMap.put("pageSize", pageSize);


        if(StringUtils.isNotBlank(name)){
            paramMap.put("name", name);
        }
        if(StringUtils.isNotBlank(idNo)){
            paramMap.put("idNo", idNo);
        }
        if(StringUtils.isNotBlank(insureType)){
            paramMap.put("insureType", insureType);
        }
        // 时间戳处理
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isNotBlank(startTime)){
            long start = Long.valueOf(startTime);
            String startStr = sdf.format(new Date(start));
            paramMap.put("startTime", startStr +" 00:00:00");
        }
        if(StringUtils.isNotBlank(endTime)){
            long end = Long.valueOf(endTime);
            String endStr = sdf.format(new Date(end));
            paramMap.put("endTime", endStr + " 23:59:59");
        }

        String result = HttpUtil.post(InsuranceConst.BASE_URL+"/insurance/pageInsurance", paramMap);
        JSONObject JSONObj = JSONObject.parseObject(result);
        if(JSONObj == null || JSONObj.getInteger("code") != 200){
            throw new CommonException("请求保险列表失败");
        }
        return ResponseResult.ok(JSONObject.parseObject(JSONObj.getString("result")));
    }


    /**
     * 保险详情
     *
     * @param id      id
     * @param request 请求
     * @return {@link ResponseResult<JSONArray>}
     */
    @GetMapping("/detail")
    @ApiOperation("保险详情")
    @ApiImplicitParam(name = "id", value = "保险ID",dataType = "String",paramType = "query")
    @AllowAnonymous
    public ResponseResult<JSONObject> fecthInsuranceInfoById(@NotBlank(message = "ID不能为空") @RequestParam String id, HttpServletRequest request){
        String sign = DigestUtils.md5DigestAsHex((id + InsuranceConst.KEY).getBytes());

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sign", sign);
        paramMap.put("id",id);
        String result= HttpUtil.post(InsuranceConst.BASE_URL+"/insurance/detailInsurance", paramMap);

        JSONObject JSONObj = JSONObject.parseObject(result);
        if(JSONObj == null || JSONObj.getInteger("code") != 200){
            log.error("请求保险信息详情失败:{}",JSONObj);
            throw new CommonException("请求保险信息详情失败");
        }
        return ResponseResult.ok(JSONObject.parseObject(JSONObj.getString("result")));
    }

}
