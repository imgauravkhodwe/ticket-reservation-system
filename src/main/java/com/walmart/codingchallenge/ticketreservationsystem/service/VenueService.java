package com.walmart.codingchallenge.ticketreservationsystem.service;

import com.walmart.codingchallenge.ticketreservationsystem.model.NumberOfSeatsDTO;
import com.walmart.codingchallenge.ticketreservationsystem.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VenueService {
    @Autowired
    Venue venue;

    @Autowired
    public VenueService() {
    }

    public ResponseEntity getTotalSeats() {
        return new ResponseEntity(new NumberOfSeatsDTO(venue.getVenueSize()), HttpStatus.OK);
    }


    /**
     * Shows the number of seats marked as AVAIL within the Venue Object.
     */
    public ResponseEntity getAvailableSeat() {
        return new ResponseEntity(new NumberOfSeatsDTO(this.getAvailableSeats()), HttpStatus.OK);
    }

    private int getAvailableSeats() {
        return venue.getAvailSeats();
    }
}
