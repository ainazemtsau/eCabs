package com.ecabs.producer.service;

import com.ecabs.common.model.Booking;
import com.ecabs.producer.dto.AddBookingDto;
import com.ecabs.producer.dto.DeleteBookingDto;
import com.ecabs.producer.dto.UpdateBookingDto;
import org.springframework.stereotype.Service;

@Service
public class ConvertServiceImpl implements ConvertService {

    @Override
    public Booking addBookingDtoToBooking(AddBookingDto bookingDto) {
        return getBookingBuilderWithoutId(bookingDto).build();
    }

    @Override
    public Booking updateBookingDtoToBooking(UpdateBookingDto bookingDto) {
        return getBookingBuilderWithoutId(bookingDto).id(bookingDto.getId()).build();
    }

    @Override
    public Booking deleteBookingDtoToBooking(DeleteBookingDto bookingDto) {
        return Booking.builder().id(bookingDto.getId()).build();
    }

    private Booking.BookingBuilder getBookingBuilderWithoutId(AddBookingDto bookingDto) {
        return Booking.builder().rating(bookingDto.getRating())
                .numberOfPassenger(bookingDto.getNumberOfPassenger())
                .passengerContactNumber(bookingDto.getPassengerContactNumber())
                .asap(bookingDto.isAsap())
                .passengerName(bookingDto.getPassengerName())
                .pickUpTime(bookingDto.getPickUpTime())
                .price(bookingDto.getPrice())
                .tripWaypointList(bookingDto.getTripWaypointList())
                .waitingTime(bookingDto.getWaitingTime());
    }
}
