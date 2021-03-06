/**
 * @author Gaurav Khodwe
 */

package com.walmart.codingchallenge.ticketreservationsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SeatHold extends Customer {

    private String seatHoldId;

    @NotNull(message = "NumberOfSeats cannot be null")
    @NotEmpty(message = "NumberOfSeats cannot be empty")
    @NotBlank(message = "NumberOfSeats cannot be blank")
    private int numberOfSeats;

    private long expirationTimer;

    private Set<Seat> seats;

    private String confirmationCode;

    /**
     * Creates an Object that is used to store and handle customer seat orders.
     * @param firstName, lastName, customerEmail these are variables provided by the customer to identify them.
     * @param seats A HashSet used to store the seats held for the SeatHold object.
     */
    public SeatHold(String firstName, String lastName, String customerEmail, Set<Seat> seats) {
        Random random = new Random();
        this.seatHoldId = UUID.randomUUID().toString();
        this.numberOfSeats = seats.size();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setCustomerEmail(customerEmail);
        // Hold ticket for 5 min
        this.expirationTimer = System.currentTimeMillis() + 300000;
        this.seats = seats;
    }

    public boolean isEmpty() {
        return this.getCustomerEmail().isEmpty() || this.getFirstName().isEmpty() || this.getLastName().isEmpty();
    }
}