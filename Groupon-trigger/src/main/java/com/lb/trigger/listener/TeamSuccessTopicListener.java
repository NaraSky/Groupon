package com.lb.trigger.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听团队成功主题消息的RabbitMQ消费者组件
 * 通过绑定指定的队列、交换器和路由键，接收并处理团队成功事件的消息
 */
@Slf4j
@Component
public class TeamSuccessTopicListener {

    /**
     * RabbitMQ消息监听方法
     * @param message 接收到的JSON格式消息内容，包含团队成功事件的详细数据
     * @return 无返回值，通过日志记录消息内容
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${spring.rabbitmq.config.producer.topic_team_success.queue}"),
                    exchange = @Exchange(value = "${spring.rabbitmq.config.producer.exchange}", type = ExchangeTypes.TOPIC),
                    key = "${spring.rabbitmq.config.producer.topic_team_success.routing_key}"
            )
    )
    public void listen(String message) {
        log.info("接收消息:{}", message);
    }

}
