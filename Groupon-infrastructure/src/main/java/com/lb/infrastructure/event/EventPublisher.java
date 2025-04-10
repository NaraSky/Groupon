package com.lb.infrastructure.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 事件发布器，负责将消息通过RabbitMQ发布到指定交换机
 * 依赖注入RabbitTemplate并使用配置的交换机名称进行消息投递
 */
@Slf4j
@Component
public class EventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.config.producer.exchange}")
    private String exchangeName;

    /**
     * 发送消息到指定路由键
     * @param routingKey 路由键，用于消息路由规则
     * @param message 待发送的消息内容
     */
    public void publish(String routingKey, String message) {
        try {
            // 发送消息并设置持久化模式确保消息持久化存储
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message, m -> {
                m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return m;
            });
        } catch (Exception e) {
            // 记录发送失败日志并抛出异常保持错误传播
            log.error("发送MQ消息失败 team_success message:{}", message, e);
            throw e;
        }
    }
}
