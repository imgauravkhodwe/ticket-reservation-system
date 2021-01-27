package com.walmart.codingchallenge.ticketreservationsystem.util;

import com.walmart.codingchallenge.ticketreservationsystem.model.Seat;
import com.walmart.codingchallenge.ticketreservationsystem.model.SeatHold;
import com.walmart.codingchallenge.ticketreservationsystem.model.Venue;
import com.walmart.codingchallenge.ticketreservationsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    @Autowired
    Venue venue;

    @Autowired
    ReservationService reservationService;

    /**
     * Remove the seats placed on hold once the expiration timer is expired
     */
    public void clearExpiredHolds() {
        for (SeatHold seatHold : reservationService.getHoldMap().values()) {
            for (Seat seat : seatHold.getSeats()) {
                if (venue.getSeat(seat.getRow(), seat.getColumn()).getStatus().equals(Constants.STATUS_ON_HOLD)
                        && seatHold.getExpirationTimer() < System.currentTimeMillis()) {
                    venue.getSeatLayout().get(seat.getRow()).remove(seat.getColumn());
                }
            }
        }
    }
}
