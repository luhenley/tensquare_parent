package com.tensquare.spit.controller;


import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 吐槽Controller
 */
@RequestMapping("/spit")
@RestController  // @RestController = @Controller+@ResponseBody
@CrossOrigin //解决前端跨域请求问题
public class SpitController {

    @Autowired
    private SpitService spitService;

    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 查询一个
     */
    @RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     * 添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Spit spit){
        spit.setId(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        spitService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据上级ID查询吐槽
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,
                                 @PathVariable int page,@PathVariable int size){
        Page<Spit> pageData = spitService.findByParentId(parentid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",
                new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 吐槽点赞
     */
    @RequestMapping(value = "/thumbup/{id}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String id){
        //模拟当前登录用户ID，模拟获取当企业登录用户id
        String userid = "1025";

        //1.从redis查询该用户是否已经点赞
        String flag = (String) redisTemplate.opsForValue().get("thumbup_" + userid + id);

        //1.1 如果已经点赞，提示用户不能重复点赞
        if (flag != null && flag.equals("1")){
            return new Result(false,StatusCode.REPEATE_ERROR,"不能重复点赞");
        }

        //1.2 如果没有点赞过，则进行点赞
        spitService.thumbup(id);

        //把用户的点赞保存redis去,并设置一天只能点赞一次
        redisTemplate.opsForValue().set("thumbup_" + userid + id,"1",1, TimeUnit.DAYS);

        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
