package com.hmall.gateway.filters;
/**
 * Author: CHAI
 * Date: 2023/11/14
 */


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 登录过滤器
 * @program: hmall
 * @author:
 * @create: 2023-11-14 21:17
 **/

@Component
public class PrintAnyGlobalFilter implements GlobalFilter , Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("GlobalFilter执行了");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
