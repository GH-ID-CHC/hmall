package com.hmall.trade.listeners;

import com.hmall.api.client.PayClient;
import com.hmall.api.dto.PayOrderDTO;
import com.hmall.common.constants.MqConstants;
import com.hmall.common.domain.MultiDelayMessage;
import com.hmall.common.mq.DelayMessageProcessor;
import com.hmall.trade.domain.po.Order;
import com.hmall.trade.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 订单状态监听
 * @program: hmall
 * @author:
 * @create: 2024-01-23 22:04
 */
@Slf4j
@RequiredArgsConstructor
public class OrderStatusListener {

    private final IOrderService orderService;

    private final RabbitTemplate rabbitTemplate;

    private final PayClient payClient;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstants.DELAY_ORDER_QUEUE,durable = "true"),
            exchange = @Exchange(name = MqConstants.DELAY_EXCHANGE,delayed = ExchangeTypes.TOPIC),
            key = MqConstants.DELAY_ORDER_ROUTING_KEY
    ))
    public void listenOrderCheckDelayMessage(MultiDelayMessage<Long> message){
        Long orderId = message.getData();
//        1.查询订单状态
        Order order = orderService.getById(orderId);



//        2.订单支付成功
        if (order ==null || order.getStatus()>1){
            log.debug("订单不存在，或者订单已支付");
            return;
        }
//todo        3.主动向支付服务查询订单状态
        PayOrderDTO payOrder = payClient.queryPayOrderByBizOrderNo(orderId);
//        4.支付，改变当前订单状态
        if (payOrder != null && payOrder.getStatus() == 3) {
            // 支付成功，更新订单状态
            orderService.markOrderPaySuccess(orderId);
            return;
        }
//        5.获取下一次的延迟时间，重新发送延迟消息
        if (message.hasNextDelay()){
            rabbitTemplate.convertAndSend(
                    MqConstants.DELAY_EXCHANGE,
                    MqConstants.DELAY_ORDER_ROUTING_KEY,
                    message,
                    new DelayMessageProcessor(message.removeNextDelay().intValue()));
        }
//        6.没有下一次的延迟消息，订单超时，恢复库存(需要使用分布式事务，放在service中进行处理)
        orderService.cancelOrder(orderId);
    }
}
