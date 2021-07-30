package com.ecabs.producer.service;

import com.ecabs.common.model.BookingResult;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsoleServiceImpl implements NotificationService {
    @Override
    public void sendEvent(BookingResult event) {
        System.out.println(event);
    }
}
