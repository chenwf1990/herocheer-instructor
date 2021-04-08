package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.WorkingReplaceCard;
import com.herocheer.instructor.enums.SysMessageEnums;
import com.herocheer.instructor.service.SysMessageService;
import com.herocheer.instructor.service.WorkingReplaceCardService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenwf
 * @desc  值班补卡(WorkingReplaceCard)表控制层
 * @date 2021-01-20 19:43:45
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/replaceCard")
@Api(tags = "补卡api")
public class WorkingReplaceCardController extends BaseController{
    @Resource
    private WorkingReplaceCardService workingReplaceCardService;
    @Autowired
    private SysMessageService sysMessageService;

    @GetMapping("/getReplaceCardList")
    @ApiOperation("获取补卡列表")
    public ResponseResult<List<WorkingReplaceCard>> getReplaceCardList(@ApiParam("值班人员id")@RequestParam Long workingScheduleUserId){
        List<WorkingReplaceCard> workingReplaceCards = workingReplaceCardService.getReplaceCardList(workingScheduleUserId);
        return ResponseResult.ok(workingReplaceCards);
    }

    @GetMapping("/get")
    @ApiOperation("根据id获取补卡信息")
    public ResponseResult<WorkingReplaceCard> get(@RequestParam Long id){

        return ResponseResult.ok(workingReplaceCardService.get(id));
    }

    @GetMapping("/approval")
    @ApiOperation("补卡审核")
    public ResponseResult approval(@ApiParam("补卡id") @RequestParam Long id,
                                                       @ApiParam("审核状态 0待审核1审核通过2审核驳回") @RequestParam int approvalStatus,
                                                       @ApiParam("审核意见") @RequestParam(required = false) String approvalComments,
                                                       HttpServletRequest request){
        UserEntity userEntity = getUser(request);
        workingReplaceCardService.approval(id,approvalStatus,approvalComments,userEntity);

        // 同步系统消息状态(不区别审核通过和驳回) 同一张表的ID不会重复
        sysMessageService.modifyMessage(Arrays.asList(SysMessageEnums.MATCH_CARD.getCode(),SysMessageEnums.STATION_CARD.getCode()), id,true,true);
        return ResponseResult.ok();
    }

    @PostMapping("/saveReplaceCard")
    @ApiOperation("添加补卡")
    public ResponseResult<WorkingReplaceCard> saveReplaceCard(@RequestBody WorkingReplaceCard workingReplaceCard,
                                                              HttpServletRequest request){
        UserEntity userEntity = getUser(request);
        return ResponseResult.isSuccess(workingReplaceCardService.saveReplaceCard(workingReplaceCard,userEntity));
    }

}