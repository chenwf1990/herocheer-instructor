package com.herocheer.instructor.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.domain.vo.CourierStationQueryVo;
import com.herocheer.instructor.domain.vo.CourierStationVo;
import com.herocheer.instructor.enums.InsuranceConst;
import com.herocheer.instructor.service.CourierStationService;
import com.herocheer.web.annotation.AllowAnonymous;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenwf
 * @desc  驿站(CourierStation)表控制层
 * @date 2021-01-04 17:26:18
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/station")
@Api(tags = "驿站管理")
public class CourierStationController extends BaseController{
    @Resource
    private CourierStationService courierStationService;

    @Value("${courier.station.url}")
    private String statioUrl;

    @PostMapping("/queryPageList")
    @ApiOperation("驿站列表列表查询")
    public ResponseResult<Page<CourierStationVo>> queryPageList(@RequestBody CourierStationQueryVo courierStationQueryVo,
                                                                HttpServletRequest request){
        UserEntity entity = getUser(request);
        courierStationQueryVo.setOpenId(entity.getOtherId());
        Page<CourierStationVo> page = courierStationService.queryPageList(courierStationQueryVo);
        return ResponseResult.ok(page);
    }

    @GetMapping("/get")
    @ApiOperation("根据id查询驿站")
    @AllowAnonymous
    public ResponseResult<CourierStation> get(@ApiParam("驿站id") @RequestParam Long id){

        return ResponseResult.ok(courierStationService.get(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增驿站")
    public ResponseResult add(@RequestBody CourierStation courierStation){
        return ResponseResult.isSuccess(courierStationService.addCourierStation(courierStation));
    }

    @PostMapping("/update")
    @ApiOperation("编辑驿站")
    public ResponseResult update(@RequestBody CourierStation courierStation){
        return ResponseResult.isSuccess(courierStationService.update(courierStation));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除驿站")
    public ResponseResult delete(@ApiParam("驿站id") @RequestParam Long id){
        return ResponseResult.isSuccess(courierStationService.deleteCourierStation(id));
    }
    @GetMapping("/QrCode")
    @ApiOperation("驿站二维码")
    @AllowAnonymous
    public void fetchQrCode(@ApiParam("驿站id") @RequestParam Long id, HttpServletResponse response) throws IOException {
        String url = StrUtil.format(statioUrl,id);

        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(0);
        // 高纠错级别
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate(url, config, "PNG",response.getOutputStream());
    }
}