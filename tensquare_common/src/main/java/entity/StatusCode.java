package entity;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-02-28
 * @Version 1.0
 */
/**
 *
 返回状态码
 */
public class StatusCode {
    public static final Integer OK = 20000;//成功
    public static final Integer ERROR = 20001;//失败
    public static final Integer USER_PASS_ERROR = 20002;//用户密码错误
    public static final Integer ACCESS_ERROR = 20003;//权限不足
    public static final Integer REMOTE_ERROR = 20004;//远程调用失败
    public static final Integer REPEATE_ERROR = 20005;//重复操作
}
