package com.fireyao.mq;

import com.fireyao.domain.UserTest;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuliyuan
 * @date 2017/9/28 17:43
 * @Description:
 */
//@Component
public class RabbitProcesse {

    private static Logger logger = Logger.getLogger(RabbitProcesse.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void process(UserTest message) {
        rabbitTemplate.convertAndSend("topicExchange", "topic.a", message);
        logger.info("--------------send topicExchange--->>>" + message + "-----------------");
    }
}
