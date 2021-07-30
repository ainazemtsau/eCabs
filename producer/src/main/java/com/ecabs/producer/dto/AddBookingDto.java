package com.ecabs.producer.dto;

import com.ecabs.common.model.TripWaypoint;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddBookingDto {
    @NotNull
    private String passengerName;
    @NotNull
    private String passengerContactNumber;
    private LocalDateTime pickUpTime;
    private boolean asap;
    private int waitingTime;
    @Positive
    private int numberOfPassenger;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal price;
    @Max(5)
    @Min(1)
    private int rating;
    @NotEmpty
    @NotNull
    @Valid
    private List<TripWaypoint> tripWaypointList;
}
