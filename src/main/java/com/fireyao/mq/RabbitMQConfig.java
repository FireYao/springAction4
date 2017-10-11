package com.fireyao.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * @author liuliyuan
 * @date 2017/9/28 17:36
 * @Description:
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue topicAnyQueue(AmqpAdmin amqpAdmin) {
        return new Queue("topic.a");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingExchangeTopicA(Queue topicAQueue, TopicExchange topicExchange) {
        //绑定指定的队列 topic.a
        return BindingBuilder.bind(topicAQueue).to(topicExchange).with("topic.a");
    }

    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost("127.0.0.1");
        factory.setUsername("fireyao");
        factory.setPassword("123456");

        factory.setConnectionThreadFactory(customizableThreadFactory());

        factory.setChannelCacheSize(20);

        return factory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }


    @Bean
    public CustomizableThreadFactory customizableThreadFactory() {
        return new CustomizableThreadFactory("rabbitmq-");
    }

}
