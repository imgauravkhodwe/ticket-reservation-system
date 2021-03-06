/**
 * @author Gaurav Khodwe
 */

package com.walmart.codingchallenge.ticketreservationsystem.controller;

import com.walmart.codingchallenge.ticketreservationsystem.TicketReservationSystemApplication;
import com.walmart.codingchallenge.ticketreservationsystem.model.NumberOfSeatsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TicketReservationSystemApplication.class)
public class VenueControllerTest {
    @Autowired
    VenueController venueController;

    @Test
    public void getTotalSeatsTest() {
        ResponseEntity<NumberOfSeatsDTO> responseEntity = venueController.getTotalSeats();
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(responseEntity.getBody().getNumberOfSeat(), 500);
    }

    @Test
    public void getAvailableSeatTest() {
        ResponseEntity<NumberOfSeatsDTO> responseEntity = venueController.getTotalSeats();
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(responseEntity.getBody().getNumberOfSeat(), 500);
    }

}
