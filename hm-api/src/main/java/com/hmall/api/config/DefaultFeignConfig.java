package com.hmall.api.config;/**
 * Author: CHAI
 * Date: 2023/11/12
 */

import feign.Logger;
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
}
