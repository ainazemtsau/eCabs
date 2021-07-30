package com.ecabs.consumer.repository;

import com.ecabs.common.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, String> {
}
