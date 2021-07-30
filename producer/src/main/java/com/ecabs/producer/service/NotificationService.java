package com.ecabs.producer.service;

import com.ecabs.common.model.BookingResult;

public interface NotificationService {

    void sendEvent(BookingResult event);
}
