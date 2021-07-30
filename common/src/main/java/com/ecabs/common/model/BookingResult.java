package com.ecabs.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResult {

    private BookingEventStatus status;
    private BookingEventName eventName;
    private String failedMessage;
    private Booking result;

    public BookingResult(BookingEventStatus status, BookingEventName eventName, Booking result) {
        this.status = status;
        this.eventName = eventName;
        this.result = result;
    }

    public BookingResult(BookingEventStatus status, BookingEventName eventName) {
        this.status = status;
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        if (status == BookingEventStatus.SUCCESS) {
            return "Event " + eventName + " is SUCCESS with result - " + result;
        } else {
            return "Event " + eventName + " is FAILED, due to " + failedMessage;
        }
    }
}
