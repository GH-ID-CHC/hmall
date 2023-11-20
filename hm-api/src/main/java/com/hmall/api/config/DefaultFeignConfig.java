package com.hmall.api.config;/**
 * Author: CHAI
 * Date: 2023/11/12
 */

import com.hmall.api.interceptors.UserInfoInterceptor;
import feign.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;



/**
 * 配置fegin的日志级别，一般配置在所需的模块，现在进行统一的抽取
 * @program: hmall
 * @author:
 * @create: 2023-11-12 20:19
 **/
public class DefaultFeignConfig {

    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }

//    启动UserInfoInterceptor的配置
    @Bean
    public UserInfoInterceptor userInfoInterceptor(){
        return new UserInfoInterceptor();
    }
}
