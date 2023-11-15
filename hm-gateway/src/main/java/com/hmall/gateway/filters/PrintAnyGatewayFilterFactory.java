package com.hmall.gateway.filters;/**
 * Author: CHAI
 * Date: 2023/11/14
 */

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 路由过滤器的例子
 * @program: hmall
 * @author:
 * @create: 2023-11-14 21:17
 **/
@Component
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
//    创建没有执行顺序的
    /*@Override
    public GatewayFilter apply(Object config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("过滤器被执行");
                return chain.filter(exchange);
            }
        };
    }*/
//    有执行顺序的需要创建OrderedGatewayFilter
    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("过滤器被执行");
                return chain.filter(exchange);
            }
        },1);
    }
}
