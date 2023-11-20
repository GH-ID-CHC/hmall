package com.hmall.common.interceptor;/**
 * Author: CHAI
 * Date: 2023/11/15
 */

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ctc.wstx.util.StringUtil;
import com.hmall.common.utils.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 所有服务的拦截器，将网关中存储的userInfo信息存在ThreadLocal中
 * @program: hmall
 * @author:
 * @create: 2023-11-15 22:22
 **/
public class UserInfoInterceptor implements HandlerInterceptor {

//    前置拦截，在请求进入controller之前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userInfo = request.getHeader("userInfo");
        if (StringUtils.isNotBlank(userInfo)){
            UserContext.setUser(Long.valueOf(userInfo));
        }
        return true;
    }
//  后置拦截，在请求离开controller之后需要进行清除用户
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
