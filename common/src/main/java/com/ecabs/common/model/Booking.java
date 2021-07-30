package com.ecabs.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Booking {
    @Id
    private String id;
    private String passengerName;
    private String passengerContactNumber;
    private LocalDateTime pickUpTime;
    private boolean asap;
    private int waitingTime;
    private int numberOfPassenger;
    private BigDecimal price;
    private double rating;
    private LocalDateTime createdOn;
    private LocalDateTime lastModifiedTime;
    @ManyToMany()
    private List<TripWaypoint> tripWaypointList;

    public Booking(String passengerName) {
        this.passengerName = passengerName;
    }
}
