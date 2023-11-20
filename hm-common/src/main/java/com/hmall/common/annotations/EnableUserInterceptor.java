package com.hmall.common.annotations;/**
 * Author: CHAI
 * Date: 2023/11/15
 */

import com.hmall.common.config.MvcConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使MvcConfig进行生效的注解，在所需的微服务的启动中添加注解，可以加载mvc的配置
 *
 *
 * @program: hmall
 * @author:
 * @create: 2023-11-15 23:21
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MvcConfig.class)
public @interface EnableUserInterceptor {

}
