package com.tensquare.base;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-02-28
 * @Version 1.0
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * 基础微服务
 */
@SpringBootApplication
@EnableEurekaClient //开启Eureka Client功能
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    @Bean  //默认单例的
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
