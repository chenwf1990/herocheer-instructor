package com.herocheer.instructor.controller;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.WorkDiary;
import com.herocheer.instructor.domain.vo.WorkDiaryVO;
import com.herocheer.instructor.service.WorkDiaryService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author gaorh
 * @desc 工作日志(WorkDiary)表控制层
 * @date 2021-06-07 17:32:37
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@RestController
@RequestMapping("/work")
@Api(tags = "工作日志")
public class WorkDiaryController extends BaseController {
    @Resource
    private WorkDiaryService workDiaryService;


    /**
     * 创建日记
     *
     * @param workDiary 工作日记
     * @param request   请求
     * @return {@link ResponseResult<WorkDiary>}
     */
    @PostMapping("/diary")
    @ApiOperation("新增工作日志")
    public ResponseResult<WorkDiary> createDiary(@ApiParam("工作日志") @RequestBody WorkDiary workDiary, HttpServletRequest request){
        // 新增工作日志
        workDiaryService.insert(workDiary);
        return ResponseResult.ok(workDiary);
    }


    /**
     * 日记工作列表
     *
     * @param workDiaryVO 工作日记签证官
     * @param request     请求
     * @return {@link ResponseResult<Page<WorkDiary>>}
     */
    @PostMapping("/diary/page")
    @ApiOperation("工作日志列表")
    public ResponseResult<Page<WorkDiary>> fetchDiaryByPage(@ApiParam("工作日志") @RequestBody WorkDiaryVO workDiaryVO, HttpServletRequest request){
        // 新增工作日志
        return ResponseResult.ok(workDiaryService.findWorkDiaryByPage(workDiaryVO));
    }
}
