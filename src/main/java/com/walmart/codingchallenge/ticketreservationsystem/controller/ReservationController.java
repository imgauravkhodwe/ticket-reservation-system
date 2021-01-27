/**
 * @author Gaurav Khodwe
 */

package com.walmart.codingchallenge.ticketreservationsystem.controller;

import com.walmart.codingchallenge.ticketreservationsystem.model.SeatHoldDTO;
import com.walmart.codingchallenge.ticketreservationsystem.model.SeatReservationDTO;
import com.walmart.codingchallenge.ticketreservationsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "/holdAvailableSeats", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity holdAvailableSeats(@Valid @RequestBody SeatHoldDTO seatHoldDTO) {
        return reservationService.findAndHoldSeats(seatHoldDTO);
    }

    @PutMapping(value = "/reserveHeldSeat", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity reserveHeldSeat(@Valid @RequestBody SeatReservationDTO seatReservationDTO) {
        return reservationService.reserveSeats(seatReservationDTO);
    }
}