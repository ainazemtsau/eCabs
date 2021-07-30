package com.ecabs.producer.controller;

import com.ecabs.common.model.Booking;
import com.ecabs.producer.dto.AddBookingDto;
import com.ecabs.producer.dto.DeleteBookingDto;
import com.ecabs.producer.dto.UpdateBookingDto;
import com.ecabs.producer.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking")
    public ResponseEntity<String> addBooking(@Valid @RequestBody AddBookingDto booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @PutMapping("/booking")
    public ResponseEntity<String> updateBooking(@Valid @RequestBody UpdateBookingDto booking) {
        return ResponseEntity.ok(bookingService.updateBooking(booking));
    }

    @DeleteMapping("/booking")
    public ResponseEntity<String> deleteBooking(@Valid @RequestBody DeleteBookingDto booking) {
        return ResponseEntity.ok(bookingService.deleteBooking(booking));
    }
}
