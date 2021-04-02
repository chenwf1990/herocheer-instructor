package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.CourseTearcher;
import com.herocheer.instructor.domain.entity.SysMessage;
import com.herocheer.instructor.domain.vo.SysMessageVO;
import com.herocheer.instructor.service.SysMessageService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author gaorh
 * @desc 系统消息通知(SysMessage)表控制层
 * @date 2021-04-01 17:17:47
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/message")
@Api(tags = "系统消息")
public class SysMessageController extends BaseController {
    @Autowired
    private SysMessageService sysMessageService;

    //TODO 消息统计

    /**
     * 消息详情
     *
     * @param id      id
     * @param request 请求
     * @return {@link ResponseResult<SysMessage>}
     */
    @GetMapping("/name/{id:\\w+}")
    @ApiOperation("消息详情")
    public ResponseResult<SysMessage> fecthMessageById(@ApiParam("消息ID") @PathVariable Long id, HttpServletRequest request){
        return ResponseResult.ok(sysMessageService.get(id));
    }
    // TODO


    /**
     * 消息列表
     *
     * @param request      请求
     * @param sysMessageVO vo
     * @return {@link ResponseResult<Page<CourseTearcher>>}
     */
    @PostMapping("/page")
    @ApiOperation("消息列表")
    public ResponseResult<Page<SysMessage>> fecthMessageByPage(@ApiParam("消息列表") @RequestBody SysMessageVO sysMessageVO, HttpServletRequest request){
        return ResponseResult.ok(sysMessageService.findMessageByPage(sysMessageVO));
    }
    /**
     * 消息已读
     *
     * @param id      id
     * @param request 请求
     * @return {@link ResponseResult}
     */
    @PutMapping("/reading/{id:\\w+}")
    @ApiOperation("消息已读")
    public ResponseResult editMessageReadStatusById(@ApiParam("消息ID") @PathVariable Long id, HttpServletRequest request){
        return ResponseResult.ok(sysMessageService.modifyMessageReadStatusById(id));
    }

    /**
     * 消息新增
     *
     * @param sysMessageVO VO
     * @param request      请求
     * @return {@link ResponseResult<SysMessage>}
     */
    @PostMapping
    @ApiOperation("新增消息")
    public ResponseResult<SysMessage> createMessage(@ApiParam("系统消息") @Valid @RequestBody SysMessageVO sysMessageVO, HttpServletRequest request){
        return ResponseResult.ok(sysMessageService.addMessage(sysMessageVO));
    }

    /**
     * 消息待办
     *
     * @param id      id
     * @param request 请求
     * @return {@link ResponseResult}
     */
    @PutMapping("/handle/{id:\\w+}")
    @ApiOperation("消息待办")
    public ResponseResult editMessageHandleStatusById(@ApiParam("消息ID") @PathVariable Long id, HttpServletRequest request){
        return ResponseResult.ok(sysMessageService.modifyMessageHandleStatusById(id));
    }
}