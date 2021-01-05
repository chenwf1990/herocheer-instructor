package com.herocheer.instructor.domain;

import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author chenwf
 * @desc 获取请求头数据
 * @date 2021/1/5
 * @company 厦门熙重电子科技有限公司
 */
@Data
public class HeaderParam {
    private String token;
    private int client;

    public HeaderParam(){
        final HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        this.token = request.getHeader("authorization");
        this.client = Integer.parseInt(request.getHeader("client"));
    }

    public static HeaderParam getInstance() {
        return new HeaderParam();
    }
}
