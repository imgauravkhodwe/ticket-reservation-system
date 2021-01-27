/**
 * @author Gaurav Khodwe
 */

package com.walmart.codingchallenge.ticketreservationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatHoldDTO extends Customer {
    @NotNull(message = "numberOfSeats cannot be null")
    @NotEmpty(message = "numberOfSeats cannot be empty")
    @NotBlank(message = "numberOfSeats cannot be blank")
    private int numberOfSeats;
}
