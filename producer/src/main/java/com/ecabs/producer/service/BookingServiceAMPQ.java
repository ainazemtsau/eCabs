package com.ecabs.producer.service;

import com.ecabs.common.model.Booking;
import com.ecabs.producer.dto.AddBookingDto;
import com.ecabs.common.model.BookingResult;
import com.ecabs.common.model.BookingEventName;
import com.ecabs.common.model.BookingEventStatus;
import com.ecabs.producer.dto.DeleteBookingDto;
import com.ecabs.producer.dto.UpdateBookingDto;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

import static constant.AMPQConstant.BOOKING_ADD_ROUTING_KEY;
import static constant.AMPQConstant.BOOKING_DELETE_ROUTING_KEY;
import static constant.AMPQConstant.BOOKING_UPDATE_ROUTING_KEY;
import static constant.AMPQConstant.MESSAGE_EXCHANGE_NAME;

@Service
public class BookingServiceAMPQ implements BookingService {

    private final AsyncRabbitTemplate rabbitTemplate;
    private final NotificationService notificationService;
    private final ConvertServiceImpl convertService;

    public BookingServiceAMPQ(AsyncRabbitTemplate rabbitTemplate, NotificationService notificationService, ConvertServiceImpl convertService) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationService = notificationService;
        this.convertService = convertService;
    }

    @Override
    public String createBooking(AddBookingDto bookingDto) {
        Booking booking = convertService.addBookingDtoToBooking(bookingDto);
        booking.setId(UUID.randomUUID().toString());
        ListenableFuture<BookingResult> asyncResult = sendMessage(booking, BOOKING_ADD_ROUTING_KEY);
        registerCallback(asyncResult, BookingEventName.ADD_BOOKING);
        return booking.getId();
    }

    @Override
    public String updateBooking(UpdateBookingDto bookingDto) {
        Booking booking = convertService.updateBookingDtoToBooking(bookingDto);
        ListenableFuture<BookingResult> asyncResult = sendMessage(booking, BOOKING_UPDATE_ROUTING_KEY);
        registerCallback(asyncResult, BookingEventName.UPDATE_BOOKING);
        return booking.getId();
    }

    @Override
    public String deleteBooking(DeleteBookingDto bookingDto) {
        Booking booking = convertService.deleteBookingDtoToBooking(bookingDto);
        ListenableFuture<BookingResult> asyncResult = sendMessage(booking, BOOKING_DELETE_ROUTING_KEY);
        registerCallback(asyncResult, BookingEventName.DELETE_BOOKING);
        return booking.getId();
    }

    private ListenableFuture<BookingResult> sendMessage(Booking booking, String routingKey) {
        return rabbitTemplate.convertSendAndReceiveAsType(MESSAGE_EXCHANGE_NAME, routingKey,
                booking, new ParameterizedTypeReference<>() {
                });
    }

    private void registerCallback(ListenableFuture<BookingResult> asyncResult, BookingEventName eventName) {
        asyncResult.addCallback(notificationService::sendEvent,
                failed -> notificationService.sendEvent(new BookingResult(BookingEventStatus.FAILED, eventName)));
    }
}
