package com.walmart.codingchallenge.ticketreservationsystem.controller;

import com.walmart.codingchallenge.ticketreservationsystem.model.NumberOfSeatsDTO;
import com.walmart.codingchallenge.ticketreservationsystem.model.SeatHold;
import com.walmart.codingchallenge.ticketreservationsystem.model.SeatHoldDTO;
import com.walmart.codingchallenge.ticketreservationsystem.model.SeatReservationDTO;
import com.walmart.codingchallenge.ticketreservationsystem.service.ReservationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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