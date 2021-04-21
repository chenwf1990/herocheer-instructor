package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.instructor.domain.entity.Field;
import com.herocheer.instructor.service.FieldService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaorh
 * @desc 场地设施表(Field)表控制层
 * @date 2021-04-21 14:33:52
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@RestController
@RequestMapping("field")
@Api(tags = "场地设施")
public class FieldController extends BaseController {
    @Autowired
    private FieldService fieldService;

    /**
     * 创建场地
     *
     * @param field   场
     * @param request 请求
     * @return {@link ResponseResult<Field>}
     */
    @PostMapping
    @ApiOperation("新增场地")
    public ResponseResult<Field> createField(@ApiParam("场地设施") @RequestBody Field field, HttpServletRequest request){
        fieldService.insert(field);
        return ResponseResult.ok(field);
    }
}