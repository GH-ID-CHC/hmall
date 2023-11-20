package com.hmall.common.config;/**
 * Author: CHAI
 * Date: 2023/11/15
 */

import com.hmall.common.interceptor.UserInfoInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 添加mvc中拦截器
 * 1. 不同的服务所定义包名称不同，导致mvcconfig无法正常进行加载，需要添加该注解类
 * 2. 需要在spring.factories添加上配置
 *
 * ConditionalOnClass在有DispatcherServlet类的情况下才会生效
 * 网关不是基于mvc,使用第二种情况，没有添加该注解也会执行该配置，所以会报错
 *
 * @program: hmall
 * @author:
 * @create: 2023-11-15 22:30
 **/
@Configuration
@ConditionalOnClass(DispatcherServlet.class)
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        默认为所有的路径，也可以进行单独设置
        registry.addInterceptor(new UserInfoInterceptor());
    }
}
