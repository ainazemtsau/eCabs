package com.ecabs.consumer.repository;

import com.ecabs.common.model.TripWaypoint;
import org.springframework.data.repository.CrudRepository;

public interface TripWaypointRepository extends CrudRepository<TripWaypoint, String> {
}
