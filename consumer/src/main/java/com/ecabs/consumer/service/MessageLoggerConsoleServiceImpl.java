package com.ecabs.consumer.service;

import com.ecabs.common.model.Booking;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static constant.AMPQConstant.MESSAGE_AUDIT_QUEUE_NAME;

@Service
public class MessageLoggerConsoleServiceImpl implements MessageLoggerService {

    @Override
    @RabbitListener(queues = MESSAGE_AUDIT_QUEUE_NAME)
    public void log(Booking booking) {
        System.out.println("Receive message with body " + booking);
    }
}
