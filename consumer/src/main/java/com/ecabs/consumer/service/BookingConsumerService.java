package com.ecabs.consumer.service;

import com.ecabs.common.model.Booking;
import com.ecabs.common.model.BookingResult;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static constant.AMPQConstant.BOOKING_DELETE_QUEUE_NAME;

public interface BookingConsumerService {

    BookingResult bookingAdd(Booking booking);

    BookingResult bookingUpdate(Booking booking);

    BookingResult bookingDelete(Booking booking);
}
