package com.ecabs.consumer.service;

import com.ecabs.common.model.Booking;

public interface MessageLoggerService {

    void log(Booking booking);
}
