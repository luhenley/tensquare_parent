package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
// 下面这个继承上面的，可以点进去源码看，下面需要实现类，
// 因为使用的是spring注入的方式，识别报错，但是不影响运行
// 当然也可以去idea设置一下警告级别
//import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-03-08
 * @Version 1.0
 */
@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    /**
     *
     添加好友
     * @param friendid
    对方用户 ID
     * @param type 1：喜欢 0：不喜欢
     * @return
     */
    /**
     * 添加好友或非好友
     */
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type){
        //1.获取当前登录用户ID
        Claims claims = (Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESS_ERROR,"请先登录");
        }

        String  userid = claims.getId();

        //2.判断type
        if(type.equals("1")){
            //添加好友
            Integer flag = friendService.addFriend(userid,friendid);
            if(flag==1){
                return new Result(true,StatusCode.OK,"添加好友成功");
            }else{
                return new Result(false,StatusCode.ERROR,"添加失败：重复了");
            }

        }else{
            //添加非好友
            friendService.addNoFriend(userid,friendid);
            return new Result(true,StatusCode.OK,"添加非好友成功");
        }

    }

    //删除好友
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
        //1.获取登录用户ID
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null){
            return new Result(false,StatusCode.ACCESS_ERROR,"请先登录");
        }

        String userid = claims.getId();

        //2.删除好友
        friendService.deleteFriend(userid,friendid);

        return new Result(true,StatusCode.OK,"删除好友成功");
    }
}
