package com.herocheer.instructor.controller;

import com.herocheer.instructor.service.ReservationMemberService;
import com.herocheer.web.base.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gaorh
 * @desc (ReservationMember)表控制层
 * @date 2021-04-20 14:26:38
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("")
@Api(tags = "")
public class ReservationMemberController extends BaseController {
    @Resource
    private ReservationMemberService reservationMemberService;


}