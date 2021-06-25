package com.herocheer.instructor.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.ReportClicks;
import com.herocheer.instructor.domain.vo.ReportClicksStatisVO;
import com.herocheer.instructor.domain.vo.ReportClicksVO;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.service.ReportClicksService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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


    /**
     * 点击量统计导出
     *
     * @param response 响应
     * @param itemType 项目类型
     * @param itemId   项id
     */
    @SneakyThrows
    @GetMapping("/clicks/excel")
    @ApiOperation("点击量统计导出")
    @AllowAnonymous
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemType", value = "项目类型",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "itemId", value = "项目名称",dataType = "long",paramType = "query")
    })
    public void clicksExcelDownload(Integer itemType, Long itemId, HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "点击量统计信息"+"-"+sdf.format(new Date());
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dateStr,"UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");

        // 导出的数据
        ReportClicksVO reportClicksVO = ReportClicksVO.builder().build();
        reportClicksVO.setItemId(itemId);
        reportClicksVO.setItemType(itemType);

        List<ReportClicksStatisVO> reportClicksList = reportClicksService.findReportClicks(reportClicksVO);
        reportClicksList.forEach(e->e.setReleaseExcelTime(DateUtil.timeStamp2DateTime(e.getReleaseTime())));
        //标题  表名  导出类型  HSSF xls  XSSF xlsx
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("点击量统计信息","点击量统计"), ReportClicksStatisVO.class, reportClicksList);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 获取单击进程id
     *
     * @param id      id
     * @param request 请求
     * @return {@link ResponseResult<Page<ReportClicksStatisVO>>}
     */
    @GetMapping("/clicks/course/{id:\\w+}")
    @ApiOperation("课程点击量")
    public ResponseResult<ReportClicksStatisVO> fetchClicksByCourseId(@ApiParam("课程ID") @PathVariable Long id, HttpServletRequest request){
        // 新增工作日志
        return ResponseResult.ok(reportClicksService.findClicksByCourseId(id));
    }
}
