package com.hmall.common.constants;

/**
 * mq常量
 * @program: hmall
 * @author:
 * @create: 2024-01-23 21:27
 */
public interface MqConstants {
    /**
     * 延迟交换机
     */
    String DELAY_EXCHANGE = "trade.delay.topic";
    /**
     * 延迟订单队列
     */
    String DELAY_ORDER_QUEUE = "trade.order.delay.queue";
    /**
     * 延迟顺序路由键
     */
    String DELAY_ORDER_ROUTING_KEY = "order.query";
}
