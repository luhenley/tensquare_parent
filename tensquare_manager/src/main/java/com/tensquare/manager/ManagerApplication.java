package com.tensquare.manager;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-03-13
 * @Version 1.0
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

/**
 * 前台用户网关
 */
@SpringBootApplication
@EnableEurekaClient // 注册到Eureka
@EnableZuulProxy  //开启Zuul功能
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
