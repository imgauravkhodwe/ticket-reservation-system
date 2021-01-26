package com.walmart.codingchallenge.ticketreservationsystem.controller;

import com.walmart.codingchallenge.ticketreservationsystem.model.NumberOfSeatsDTO;
import com.walmart.codingchallenge.ticketreservationsystem.service.ReservationService;
import com.walmart.codingchallenge.ticketreservationsystem.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VenueController {
    private final VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping(value = "/totalSeats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTotalSeats() {
        return venueService.getTotalSeats();
    }

    @GetMapping(value = "/availableSeats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAvailableSeats() {
        return venueService.getAvailableSeat();
    }
}
