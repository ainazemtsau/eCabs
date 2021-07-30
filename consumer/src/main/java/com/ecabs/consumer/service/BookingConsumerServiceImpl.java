package com.ecabs.consumer.service;

import com.ecabs.common.model.Booking;
import com.ecabs.common.model.BookingEventName;
import com.ecabs.common.model.BookingEventStatus;
import com.ecabs.common.model.BookingResult;
import com.ecabs.common.model.TripWaypoint;
import com.ecabs.consumer.repository.BookingRepository;
import com.ecabs.consumer.repository.TripWaypointRepository;
import javassist.NotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static constant.AMPQConstant.BOOKING_ADD_QUEUE_NAME;
import static constant.AMPQConstant.BOOKING_DELETE_QUEUE_NAME;
import static constant.AMPQConstant.BOOKING_UPDATE_QUEUE_NAME;

@Service
public class BookingConsumerServiceImpl implements BookingConsumerService {

    private final BookingRepository bookingRepository;
    private final TripWaypointRepository tripWaypointRepository;

    public BookingConsumerServiceImpl(BookingRepository bookingRepository, TripWaypointRepository tripWaypointRepository) {
        this.bookingRepository = bookingRepository;
        this.tripWaypointRepository = tripWaypointRepository;
    }

    @Override
    @RabbitListener(queues = BOOKING_ADD_QUEUE_NAME)
    @Transactional
    public BookingResult bookingAdd(Booking booking) {
        booking.setCreatedOn(LocalDateTime.now());
        booking.setLastModifiedTime(LocalDateTime.now());
        return getBookingResult(booking, BookingEventName.ADD_BOOKING);
    }

    @Override
    @RabbitListener(queues = BOOKING_UPDATE_QUEUE_NAME)
    public BookingResult bookingUpdate(Booking booking) {
        booking.setLastModifiedTime(LocalDateTime.now());
        if (!bookingRepository.existsById(booking.getId())) {
            return BookingResult.builder().eventName(BookingEventName.UPDATE_BOOKING).status(BookingEventStatus.FAILED)
                    .failedMessage("Booking with " + booking.getId() + "does not exists").build();
        }
        return getBookingResult(booking, BookingEventName.UPDATE_BOOKING);
    }

    @Override
    @RabbitListener(queues = BOOKING_DELETE_QUEUE_NAME)
    public BookingResult bookingDelete(Booking booking) {
        bookingRepository.delete(booking);
        if (!bookingRepository.existsById(booking.getId())) {
            return BookingResult.builder().eventName(BookingEventName.DELETE_BOOKING).status(BookingEventStatus.FAILED)
                    .failedMessage("Booking with " + booking.getId() + "does not exists").build();
        }
        return BookingResult.builder().result(booking).eventName(BookingEventName.DELETE_BOOKING).status(BookingEventStatus.SUCCESS).build();
    }

    private BookingResult getBookingResult(Booking booking, BookingEventName eventName) {
        List<TripWaypoint> checkedTripWaypointList;
        try {
            checkedTripWaypointList = getCheckedTripWaypointList(booking);
        } catch (RuntimeException exception) {
            return BookingResult.builder().failedMessage(exception.getMessage())
                    .eventName(eventName).status(BookingEventStatus.FAILED).build();
        }
        booking.setTripWaypointList(checkedTripWaypointList);
        Booking result = bookingRepository.save(booking);
        return BookingResult.builder().result(result).eventName(eventName).status(BookingEventStatus.SUCCESS).build();
    }

    private List<TripWaypoint> getCheckedTripWaypointList(Booking booking) {
        return booking.getTripWaypointList().stream().map(point -> {
            if (point.getId() == null) {
                point.setId(UUID.randomUUID().toString());
            } else if (!tripWaypointRepository.existsById(point.getId())) {
                throw new RuntimeException("Trip waypoint not found with such id");
            }
            return tripWaypointRepository.save(point);
        }).collect(Collectors.toList());
    }
}
