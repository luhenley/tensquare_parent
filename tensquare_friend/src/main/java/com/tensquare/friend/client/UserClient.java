package com.tensquare.friend.client;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-03-09
 * @Version 1.0
 */

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 远程接口 用户客户端
 */
/*
@FeignClient 注解用于指定从哪个服务中调用功能
@RequestMapping 注解用于对被调用的微服务进行地址映射。
注意 @PathVariable 注解一定要指定参数名称，否则出错
*/

@FeignClient("tensquare-user")
public interface UserClient {
    @RequestMapping(value = "/user/updateFollowcount/{userid}/{x}",method = RequestMethod.PUT)
    public Result updateFollowcount(@PathVariable("userid") String userid,@PathVariable("x") int x);

    @RequestMapping(value = "/user/updateFanscount/{userid}/{x}",method = RequestMethod.PUT)
    public Result updateFanscount(@PathVariable("userid") String userid,@PathVariable("x") int x);
}


