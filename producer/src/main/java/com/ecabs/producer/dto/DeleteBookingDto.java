package com.ecabs.producer.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
public class DeleteBookingDto {
    @NotNull
    private String id;
}
