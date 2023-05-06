package com.tensquare.user.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-03-07
 * @Version 1.0
 */
@Component
public class JwtFilter extends HandlerInterceptorAdapter{
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        final String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            final String token = authHeader.substring(7); // The part after "Bearer

            Claims claims = jwtUtil.parseJWT(token);//解析token，拿到载荷
            if (claims != null){
                if("admin".equals(claims.get("roles"))){//如果是管理员
                    request.setAttribute("admin_claims",claims);
                }
                if ("user".equals(claims.get("roles"))){//如果是用户
                    request.setAttribute("user_claims",claims);
                }
            }
        }
        return true;
    }
}
