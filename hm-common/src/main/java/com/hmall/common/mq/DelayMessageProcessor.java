package com.hmall.common.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * 设置延迟消息
 *
 * 使用构造函数为delay赋值
 * @program: hmall
 * @author:
 * @create: 2024-01-23 21:34
 */
@RequiredArgsConstructor
public class DelayMessageProcessor implements MessagePostProcessor {

    private final int delay;

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setDelay(delay);
        return message;
    }
}
