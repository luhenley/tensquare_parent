package com.tensquare.manager;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-03-13
 * @Version 1.0
 */
@Component
public class ManageFilter extends ZuulFilter {
    /**
     * 过滤器类型
     *   pre: 在网关执行之前
     *   route：在网关执行中
     *   post： 在网关执行后
     *   error：在网关执行错误的时候
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 执行顺序
     *  数组越大，优先级越低
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行run方法
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Autowired
    private JwtUtil jwtUtil;
    /**
     * 执行逻辑代码
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //System.out.println("是否执行了WebFilter过滤器...");
        System.out.println("Zuul 过滤器");

        //1.从用户请求获取到Authorization信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //放行管理员登录请求  /admin/login
        String uri = request.getRequestURI().toString();// 获取请求地址

        if(uri.contains("/admin/login")){
            System.out.println("登录页面" + uri);
            //放行
            return null;
        }

        String auth = request.getHeader("Authorization");
        //2.手动把Authorization信息放回到zuul的请求（转发后的请求）里面
        if (auth!=null){
            //2.判断是否以Bearer开头
            if(auth.startsWith("Bearer")){
                //3.取出token
                String token = auth.substring(7);

                //4.使用jjwt工具校验
                Claims body = jwtUtil.parseJWT(token);
                System.out.println(body);

                if (body!=null){
                    //5.判断是否为管理员身份
                    if (body.get("roles").equals("admin")){
                        //6.放行，转发到微服务
                        return null;
                    }
                }
            }
            //requestContext.addZuulRequestHeader("Authorization",auth);
        }

        //如果不是管理员，中止转发，响应提示信息“你不是管理员，权限不足!!”
        //中止请求转发(后面即使有return null也不转发啦)
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseBody("你不是管理员，权限不足!!");
        //requestContext.getResponse().getWriter().write("你不是管理员，权限不足!!");
        //设置输出内容类型和编码
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        //return null代表放行
        return null;
    }
}
