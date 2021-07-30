package com.ecabs.producer.service;

import com.ecabs.common.model.Booking;
import com.ecabs.producer.dto.AddBookingDto;
import com.ecabs.producer.dto.DeleteBookingDto;
import com.ecabs.producer.dto.UpdateBookingDto;

public interface BookingService {

    String createBooking(AddBookingDto booking);

    String updateBooking(UpdateBookingDto booking);

    String deleteBooking(DeleteBookingDto booking);
}
