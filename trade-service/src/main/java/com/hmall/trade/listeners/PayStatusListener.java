package com.hmall.trade.listeners;

import com.hmall.trade.domain.po.Order;
import com.hmall.trade.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * mq监听消息
 * @program: hmall
 * @author:
 * @create: 2024-01-14 16:53
 */
@Component
@RequiredArgsConstructor
public class PayStatusListener {

    private final IOrderService iOrderService;

    /**
     * listner mq
     * 获取支付成功的订单id
     * @param orderId 订单id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "mark.order.pay.queue",durable = "true"),
            exchange = @Exchange(name = "pay.topic",type = ExchangeTypes.TOPIC),
            key = {"pay.success"}
    ))
    public void listnerMq(Long orderId){

        Order order = iOrderService.getById(orderId);

        if (order == null || order.getStatus()!=1){
            return;
        }

        //把订单状态修改为已支付
        iOrderService.markOrderPaySuccess(orderId);
    }
}
