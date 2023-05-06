package com.tensquare.qa.client.impl;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-03-12
 * @Version 1.0
 */

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * 本地的熔断器类
 */
@Component // 将该类放进Spring容器
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.OK,"远程服务失效啦，返回的是临时数据");
    }
}
