package com.lot.server.interceptor;

import com.lot.server.common.constant.JwtClaimsConstant;
import com.lot.server.common.context.UserContext;
import com.lot.server.common.properties.JwtProperties;
import com.lot.server.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            // if not controller
            return true;
        }

        // get token
        String token = request.getHeader(jwtProperties.getTokenName());

        // check token
        try {
            Claims claims = JwtUtils.parseJWT(jwtProperties.getSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            UserContext.setUserId(userId);
            return true;
        } catch (Exception ex) {
            // no authorization
            response.setStatus(402);
            return false;
        }
    }
}
