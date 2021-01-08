package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.RecruitDetail;
import com.herocheer.instructor.domain.entity.RecruitInfo;
import com.herocheer.instructor.domain.entity.ServiceHours;
import com.herocheer.instructor.domain.vo.RecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.RecruitInfoVo;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.enums.RecruitStateEnums;
import com.herocheer.instructor.enums.RecruitTypeEunms;
import com.herocheer.instructor.service.RecruitDetailService;
import com.herocheer.instructor.service.RecruitInfoService;
import com.herocheer.instructor.service.ServiceHoursService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  招募信息主表 (RecruitInfo)表控制层
 * @date 2021-01-06 17:32:02
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/recruit")
@Api(tags = "招募信息管理")
public class RecruitInfoController extends BaseController{
    @Resource
    private RecruitInfoService recruitInfoService;

    @Resource
    private ServiceHoursService serviceHoursService;

    @Resource
    private RecruitDetailService recruitDetailService;


    @PostMapping("/queryPageList")
    @ApiOperation("查询招募信息")
    public ResponseResult<Page<RecruitInfo>> queryPageList(@RequestBody RecruitInfoQueryVo queryVo){
        Page<RecruitInfo> serviceHours= recruitInfoService.queryPageList(queryVo);
        return ResponseResult.ok().setData(serviceHours);
    }

    @GetMapping("/get")
    @ApiOperation("查询招募信息详情")
    public ResponseResult<RecruitInfoVo> get(@ApiParam("新闻id") @RequestParam Long id){
        RecruitInfoVo recruitInfo=recruitInfoService.getRecruitInfoVoById(id);
        if(recruitInfo!=null){
            if(recruitInfo.getServiceHoursId()!=null){
                ServiceHours serviceHours=serviceHoursService.get(recruitInfo.getServiceHoursId());
                recruitInfo.setServiceHours(serviceHours.getServiceTimes());
            }
            if(RecruitTypeEunms.MATCH_RECRUIT.getType()==recruitInfo.getRecruitType()){
                Map<String,Object> map=new HashMap<>();
                map.put("recruitId",id);
                List<RecruitDetail> recruitDetailList= recruitDetailService.findByLimit(map);
                recruitInfo.setRecruitDetails(recruitDetailList);
            }
        }
        return ResponseResult.ok().setData(recruitInfo);
    }

    @PostMapping("/add")
    @ApiOperation("新增招募信息")
    public ResponseResult add(@RequestBody RecruitInfoVo recruitInfoVo){
        //设置审核状态为待审核
        recruitInfoVo.setStatus(RecruitStateEnums.PENDING.getState());
        Long count=recruitInfoService.insert(recruitInfoVo);
        if(RecruitTypeEunms.MATCH_RECRUIT.getType()==recruitInfoVo.getRecruitType()&&recruitInfoVo.getRecruitDetails()!=null){
            for(RecruitDetail recruitDetail:recruitInfoVo.getRecruitDetails()){
                recruitDetail.setRecruitId(recruitInfoVo.getId());
                recruitDetailService.insert(recruitDetail);
            }
        }
        return ResponseResult.isSuccess(count);
    }

    @PostMapping("/update")
    @ApiOperation("修改招募信息")
    public ResponseResult update(@RequestBody RecruitInfoVo recruitInfoVo){
        Long count=recruitInfoService.update(recruitInfoVo);
        if(RecruitTypeEunms.MATCH_RECRUIT.getType()==recruitInfoVo.getRecruitType()&&recruitInfoVo.getRecruitDetails()!=null){
            for(RecruitDetail recruitDetail:recruitInfoVo.getRecruitDetails()){
                recruitDetail.setRecruitId(recruitInfoVo.getId());
                if(recruitDetail.getId()!=null){
                    recruitDetailService.update(recruitDetail);
                }else {
                    recruitDetailService.insert(recruitDetail);
                }
            }
        }
        return ResponseResult.isSuccess(count);
    }
}