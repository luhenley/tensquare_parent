package entity;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-02-28
 * @Version 1.0
 */

import java.io.Serializable;

/**
 * 封装后端返回前端的数据
 */
public class Result implements Serializable {

    private Boolean flag;//是否成功
    private Integer code;//返回码
    private String message;//提示信息
    private Object data;//返回数据内容（查询操作才会有）

    public Result() {
    }

    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(Boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
