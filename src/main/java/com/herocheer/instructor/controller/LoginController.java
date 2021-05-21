package com.herocheer.instructor.controller;

import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.InstructorApply;
import com.herocheer.instructor.domain.entity.InstructorApplyAuditLog;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.service.InstructorApplyAuditLogService;
import com.herocheer.instructor.service.InstructorApplyService;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.instructor.service.LoginService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.utils.AesUtil;
import com.herocheer.web.annotation.AllowAnonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/26
 * @company 厦门熙重电子科技有限公司
 */
@RestController
@RequestMapping("/login")
@Api(tags = "测试类")
public class LoginController {
    @Resource
    private LoginService loginService;
    @Resource
    private InstructorService instructorService;
    @Resource
    private InstructorApplyService instructorApplyService;
    @Resource
    private InstructorApplyAuditLogService instructorApplyAuditLogService;
    @Resource
    private UserService userService;

    @GetMapping("/aes")
    @ApiOperation("AES加密/解密")
    @AllowAnonymous
    public ResponseResult add(@ApiParam("需要加密解密的文本") @RequestParam String text) {
        String aesStr;
        if(text.length() == 18){
            aesStr = AesUtil.encrypt(text);
        }else{
            aesStr = AesUtil.decrypt(text);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("text",text);
        map.put("aesStr",aesStr);
        return ResponseResult.ok(map);
    }

    @GetMapping("/refreshCardNoAndPhone")
    @ApiOperation("刷身份证/手机号码加密")
    @AllowAnonymous
    public ResponseResult refreshCardNoAndPhone() {
        Map<String,Object> map = new HashMap<>();
        List<Instructor> instructorList = this.instructorService.findByLimit(map);
        List<InstructorApply> instructorApplyList = this.instructorApplyService.findByLimit(map);
        List<InstructorApplyAuditLog> instructorApplyAuditLogList = this.instructorApplyAuditLogService.findByLimit(map);
        List<User> users = userService.findByLimit(map);
        for (Instructor instructor : instructorList) {
            Instructor update = new Instructor();
            update.setId(instructor.getId());
            boolean b = false;
            if(!StringUtils.isEmpty(instructor.getPhone()) && instructor.getPhone().length() == 11){
                b = true;
                update.setPhone(AesUtil.encrypt(instructor.getPhone()));
            }
            if(!StringUtils.isEmpty(instructor.getCardNo()) && instructor.getCardNo().length() == 18){
                b = true;
                update.setCardNo(AesUtil.encrypt(instructor.getCardNo()));
            }
            if(b) {
                instructorService.update(update);
            }
        }
        for (InstructorApply instructor : instructorApplyList) {
            InstructorApply update = new InstructorApply();
            update.setId(instructor.getId());
            boolean b = false;
            if(!StringUtils.isEmpty(instructor.getPhone()) && instructor.getPhone().length() == 11){
                b = true;
                update.setPhone(AesUtil.encrypt(instructor.getPhone()));
            }
            if(!StringUtils.isEmpty(instructor.getCardNo()) && instructor.getCardNo().length() == 18){
                b = true;
                update.setCardNo(AesUtil.encrypt(instructor.getCardNo()));
            }
            if(b) {
                instructorApplyService.update(update);
            }
        }
        for (InstructorApplyAuditLog instructor : instructorApplyAuditLogList) {
            InstructorApplyAuditLog update = new InstructorApplyAuditLog();
            update.setId(instructor.getId());
            boolean b = false;
            if(!StringUtils.isEmpty(instructor.getPhone()) && instructor.getPhone().length() == 11){
                b = true;
                update.setPhone(AesUtil.encrypt(instructor.getPhone()));
            }
            if(!StringUtils.isEmpty(instructor.getCardNo()) && instructor.getCardNo().length() == 18){
                b = true;
                update.setCardNo(AesUtil.encrypt(instructor.getCardNo()));
            }
            if(b) {
                instructorApplyAuditLogService.update(update);
            }
        }
        for (User user : users) {
            User update = new User();
            update.setId(user.getId());
            boolean b = false;
            if(!StringUtils.isEmpty(user.getPhone()) && user.getPhone().length() == 11){
                b = true;
                update.setPhone(AesUtil.encrypt(user.getPhone()));
            }
            if(!StringUtils.isEmpty(user.getCertificateNo()) && user.getCertificateNo().length() == 18){
                b = true;
                update.setCertificateNo(AesUtil.encrypt(user.getCertificateNo()));
            }
            if(b) {
                userService.update(update);
            }
        }
        return ResponseResult.ok(map);
    }

    /**
     * 模板下载
     * @param request
     * @return
     */
    @GetMapping("/downloadTemplate")
    @ApiOperation("模板下载")
    @AllowAnonymous
    public ResponseEntity<byte[]> downloadTemplate(HttpServletRequest request){
        ResponseEntity<byte[]> entity = null;
        try {
//            ClassPathResource resource = new ClassPathResource("建行订单状态为成功订单但赛事报名的支付状态已关闭名单.xlsx");
//            InputStream is = resource.getInputStream();

//            InputStream is = this.getClass().getResourceAsStream("/建行订单状态为成功订单但赛事报名的支付状态已关闭名单.xlsx");
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("建行订单状态为成功订单但赛事报名的支付状态已关闭名单.xlsx");
            byte[] body =  new byte[is.available()];
            is.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode("文件名.xlsx","utf-8"));
            headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            HttpStatus statusCode = HttpStatus.OK;
            entity = new ResponseEntity<byte[]>(body, headers, statusCode);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }

}
