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
public class SeatReservationDTO extends Customer {
    @NotNull(message = "seatHoldId must not be null")
    @NotEmpty(message = "seatHoldId must not be empty")
    @NotBlank(message = "seatHoldId must not be blank")
    private String seatHoldId;
}
