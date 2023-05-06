package com.tensquare.friend.filter;

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
/**
 * JWT权限拦截器
 */
@Component
public class JwtFilter extends HandlerInterceptorAdapter{
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 执行进行Controller的方法之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("经过了拦截器");
        //1 使用jjwt解析token字符串，是否合法（是否过期）
        //1.1 判断请求头是否有Authorization
        final String authHeader = request.getHeader("Authorization");
        //1.2 判断auth是否以Bearer开头
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            //1.3 截取出Token字符串
            final String token = authHeader.substring(7); // The part after "Bearer

            //1.4 解析Token字符串是否合法（是否过期）
            Claims claims = jwtUtil.parseJWT(token);//解析token，拿到载荷
            //验证通过啦
            if (claims != null){
                if("admin".equals(claims.get("roles"))){//2 从载荷获取roles，如果是管理员
                    request.setAttribute("admin_claims",claims);//给标记
                }
                if ("user".equals(claims.get("roles"))){//如果是用户
                    request.setAttribute("user_claims",claims);//给标记
                }
            }
        }
        //放行
        return true;
    }
}
