package com.tensquare.sms;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-03-06
 * @Version 1.0
 */

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信监听
 */
@Component //该注解是将这个类放进spring容器里面
@RabbitListener(queues = "itcast") //选择监听itcast队列
public class SmsListener {

    @Value("${aliyun.sms.sign_name}")
    private String signName;

    @Value("${aliyun.sms.temp_code}")
    private String tempCode;

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void handleMessage(Map data){
        System.out.println("手机号码："+data.get("mobile"));
        System.out.println("验证码："+data.get("code"));

        try{
            SendSmsResponse response = smsUtil.sendSms( (String)data.get("mobile") , tempCode,signName,"{\"code\":\""+data.get("code")+"\"}");
            if (response.getCode().equals("OK")){
                System.out.println("短信发送成功");
            }else {
                System.out.println("短信发送失败："+response.getCode());
            }
        }catch (ClientException e){
            e.printStackTrace();
        }
    }
}
