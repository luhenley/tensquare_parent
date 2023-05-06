package com.tensquare.friend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;
/*
 * 交友微服务
 *
 **/

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient //开启服务提供者或消费者，客户端的支持，用来注册服务或连接到如Eureka之类的注册中心
@EnableFeignClients  //开启Feign功能
public class FriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendApplication.class, args);
	}

	@Bean	//默认单例的
	public IdWorker idWorker(){
		return new IdWorker(1, 1);
	}

	@Bean
	public JwtUtil jwtUtil(){
		return new JwtUtil();
	}
}
