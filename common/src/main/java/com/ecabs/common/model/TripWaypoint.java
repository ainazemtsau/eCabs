package com.ecabs.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
public class TripWaypoint {
    @Id
    private String  id;
    @NotNull
    @NotBlank
    private String locality;
    private double latitude;
    private double longitude;

    public TripWaypoint(String locality, double latitude, double longitude) {
        this.locality = locality;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
