package com.ecabs.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static constant.AMPQConstant.BOOKING_ADD_QUEUE_NAME;
import static constant.AMPQConstant.BOOKING_ADD_ROUTING_KEY;
import static constant.AMPQConstant.BOOKING_DELETE_QUEUE_NAME;
import static constant.AMPQConstant.BOOKING_DELETE_ROUTING_KEY;
import static constant.AMPQConstant.BOOKING_EXCHANGE_NAME;
import static constant.AMPQConstant.BOOKING_UPDATE_QUEUE_NAME;
import static constant.AMPQConstant.BOOKING_UPDATE_ROUTING_KEY;
import static constant.AMPQConstant.MESSAGE_AUDIT_QUEUE_NAME;
import static constant.AMPQConstant.MESSAGE_EXCHANGE_NAME;

@Configuration
public class MessageEventConfiguration {

    @Bean
    public Queue messageQueue() {
        return new Queue(MESSAGE_AUDIT_QUEUE_NAME, false);
    }

    @Bean
    public Binding auditMessageBinding(Queue messageQueue, FanoutExchange messageExchange) {
        return BindingBuilder.bind(messageQueue).to(messageExchange);
    }

    @Bean
    FanoutExchange messageExchange() {
        return new FanoutExchange(MESSAGE_EXCHANGE_NAME);
    }

    @Bean
    DirectExchange bookingExchange() {
        return new DirectExchange(BOOKING_EXCHANGE_NAME);
    }


    @Bean
    Binding exchangeBinding(DirectExchange bookingExchange, FanoutExchange messageExchange) {
        return BindingBuilder.bind(bookingExchange).to(messageExchange);
    }

    @Bean
    Queue addQueue() {
        return new Queue(BOOKING_ADD_QUEUE_NAME, false);
    }

    @Bean
    Queue updateQueue() {
        return new Queue(BOOKING_UPDATE_QUEUE_NAME, false);
    }

    @Bean
    Queue deleteQueue() {
        return new Queue(BOOKING_DELETE_QUEUE_NAME, false);
    }


    @Bean
    Binding bookingBinding(Queue addQueue, DirectExchange bookingExchange) {
        return BindingBuilder.bind(addQueue).to(bookingExchange).with(BOOKING_ADD_ROUTING_KEY);
    }

    @Bean
    Binding updateBinding(Queue updateQueue, DirectExchange bookingExchange) {
        return BindingBuilder.bind(updateQueue).to(bookingExchange).with(BOOKING_UPDATE_ROUTING_KEY);
    }

    @Bean
    Binding deleteBinding(Queue deleteQueue, DirectExchange bookingExchange) {
        return BindingBuilder.bind(deleteQueue).to(bookingExchange).with(BOOKING_DELETE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        return new Jackson2JsonMessageConverter(mapper);
    }
}
