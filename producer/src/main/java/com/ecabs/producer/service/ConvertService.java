package com.ecabs.producer.service;

import com.ecabs.common.model.Booking;
import com.ecabs.producer.dto.AddBookingDto;
import com.ecabs.producer.dto.DeleteBookingDto;
import com.ecabs.producer.dto.UpdateBookingDto;

public interface ConvertService {

    Booking addBookingDtoToBooking(AddBookingDto bookingDto);
    Booking updateBookingDtoToBooking(UpdateBookingDto bookingDto);
    Booking deleteBookingDtoToBooking(DeleteBookingDto bookingDto);
}
