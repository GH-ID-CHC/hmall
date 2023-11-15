package com.hmall.gateway.filters;/**
 * Author: CHAI
 * Date: 2023/11/15
 */

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.AntPathMatcher;
import com.hmall.common.utils.CollUtils;
import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 全局过滤器的例子
 * @program: hmall
 * @author:
 * @create: 2023-11-15 21:14
 **/
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;

    private final JwtTool jwtTool;

    private final AntPathMatcher antPathMatcher=new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取request
        ServerHttpRequest request = exchange.getRequest();

        HttpHeaders headers = request.getHeaders();
        //判断请求是否需要拦截
        if (isExclude(request.getPath().toString())) {
            //符合条件的路径直接进行放行
            return chain.filter(exchange);
        }
        //获取请求头中的token
        String token = null;
        List<String> authorization = headers.get("authorization");
        if (!CollUtil.isEmpty(authorization)) {
            token = authorization.get(0);
        }

//        判断请求头中的token是否符合
        Long userId=null;
        try {
            userId = jwtTool.parseToken(token);
        } catch (Exception e) {
//            解析失败，进行拦截
            ServerHttpResponse response = exchange.getResponse();
            response.setRawStatusCode(401);
            return response.setComplete();
        }
        String userInfo = userId.toString();
        ServerWebExchange exchangeNew = exchange.mutate().request(builder -> builder.header("userInfo", userInfo)).build();
//        解析通过,放行
        return chain.filter(exchangeNew);
    }

    /**
     * 是排除
     * 判断请求路径，是否符合放行路径
     *
     * @param antPath 请求路径
     * @return boolean
     */
    private boolean isExclude(String antPath) {
        for (String pathPattern : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(pathPattern,antPath)){
                return true;
            }
        }
        return false;
    }

    /**
     * 定制执行顺序
     *
     * @return int
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
