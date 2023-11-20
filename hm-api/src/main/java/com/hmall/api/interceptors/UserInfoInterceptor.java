package com.hmall.api.interceptors;
/**
 * Author: CHAI
 * Date: 2023/11/20
 */

import com.hmall.common.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * openfeign的拦截器，保存用户的信息到请求头中
 * @program: hmall
 * @author:
 * @create: 2023-11-20 22:03
 **/
public class UserInfoInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long user = UserContext.getUser();
        if (user==null){
            return;
        }
        requestTemplate.header("userInfo",user.toString());
    }
}
