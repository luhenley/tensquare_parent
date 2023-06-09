package com.tensquare.user.controller;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	


    /**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}


	@Autowired
	private HttpServletRequest request;
	@Autowired
	private JwtUtil jwtUtil;
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		/*String authHeader = request.getHeader("Authorization");//获取请求头信息
		if (authHeader == null){
			return new Result(false,StatusCode.ACCESS_ERROR,"权限不足");
		}
		if (!authHeader.startsWith("Bearer ")){
			return new Result(false,StatusCode.ACCESS_ERROR,"权限不足");
		}
		String token = authHeader.substring(7);//提取token
		Claims claims = jwtUtil.parseJWT(token);
		if(claims == null){
			return new Result(false,StatusCode.ACCESS_ERROR,"权限不足");
		}
		if (!"admin".equals(claims.get("roles"))){
			return new Result(false,StatusCode.ACCESS_ERROR,"权限不足");
		}*/
		Claims claims = (Claims )request.getAttribute("admin_claims");

		if (claims == null){
			return new Result(true,StatusCode.ACCESS_ERROR,"无权访问");
		}
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 发送验证码
	 *
	 * @param mobile  手机号码
	 * @return entity.Result
	 **/
	@RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
	public Result sendsms(@PathVariable String mobile){
		userService.sendsmd(mobile);
		return new Result(true,StatusCode.OK,"验证码发送成功");
	}

	/**
	 * 注册
	 */
	@RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
	public Result register(@PathVariable String code,@RequestBody User user){
		Boolean flag = userService.register(code, user);
		if (flag){
			return new Result(true,StatusCode.OK,"注册成功");
		}else {
			return new Result(false,StatusCode.ERROR,"验证码输入有误");
		}
	}

	/**
	 * 登录
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		User loginUser = userService.login(user);
		if (loginUser==null){
			return new Result(false,StatusCode.USER_PASS_ERROR,"登录失败；用户或密码错误");
		}else{
			String token = jwtUtil.createJWT(loginUser.getId(),loginUser.getNickname(),"user");
			Map map = new HashMap();
			map.put("token",token);
			map.put("name",loginUser.getNickname());
			map.put("avatar",loginUser.getAvatar());
			return new Result(true,StatusCode.OK,"登录成功",map);
		}
	}

	/**
	 * 更新关注数
	 */
	@RequestMapping(value = "/updateFollowcount/{userid}/{x}",method = RequestMethod.PUT)
	public Result updateFollowcount(@PathVariable String userid,@PathVariable int x){
		userService.updateFollowcount(userid,x);
		return new Result(true,StatusCode.OK,"更新关注数成功");

	}

	/**
	 * 更新粉丝数
	 */
	@RequestMapping(value = "/updateFanscount/{userid}/{x}",method = RequestMethod.PUT)
	public Result updateFanscount(@PathVariable String userid,@PathVariable int x){
		userService.updateFanscount(userid,x);
		return new Result(true,StatusCode.OK,"更新粉丝数成功");
	}
}
