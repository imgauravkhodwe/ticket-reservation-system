package com.walmart.codingchallenge.ticketreservationsystem.service;

import com.walmart.codingchallenge.ticketreservationsystem.TicketReservationSystemApplication;
import com.walmart.codingchallenge.ticketreservationsystem.model.SeatHold;
import com.walmart.codingchallenge.ticketreservationsystem.model.SeatHoldDTO;
import com.walmart.codingchallenge.ticketreservationsystem.model.SeatReservationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TicketReservationSystemApplication.class)
public class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;

    private SeatHoldDTO seatHoldDTO = new SeatHoldDTO();

    private SeatReservationDTO seatReservationDTO = new SeatReservationDTO();

    @BeforeEach
    public void setup() {
        seatHoldDTO.setCustomerEmail("abc@gmail.com");
        seatHoldDTO.setFirstName("firstName");
        seatHoldDTO.setLastName("lastName");

        seatReservationDTO.setCustomerEmail("abc@gmail.com");
        seatReservationDTO.setFirstName("firstName");
        seatReservationDTO.setLastName("lastName");
    }

    @Test
    public void findAndHoldSeatsTest() {
        seatHoldDTO.setNumberOfSeats(5);
        ResponseEntity<SeatHold> responseEntity = reservationService.findAndHoldSeats(seatHoldDTO);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertNull(responseEntity.getBody().getConfirmationCode());
        assertEquals(5, responseEntity.getBody().getNumberOfSeats());
        assertEquals("firstName", responseEntity.getBody().getFirstName());
        assertEquals("lastName", responseEntity.getBody().getLastName());
        assertEquals("abc@gmail.com", responseEntity.getBody().getCustomerEmail());
    }

    @Test
    public void findAndHoldZeroSeatsTest() {
        seatHoldDTO.setNumberOfSeats(0);
        ResponseEntity responseEntity = reservationService.findAndHoldSeats(seatHoldDTO);
        assertTrue(responseEntity.getStatusCode().is4xxClientError());
        assertEquals("You must hold at least 1 seat.", responseEntity.getBody());
    }

    @Test
    public void findAndHoldOutOfCapacitySeatsTest() {
        seatHoldDTO.setNumberOfSeats(9999);
        ResponseEntity responseEntity = reservationService.findAndHoldSeats(seatHoldDTO);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("Not enough seats are left to fulfill your order.", responseEntity.getBody());
    }

    @Test
    public void reserveSeatsWithWrongIDTest() {
        seatReservationDTO.setSeatHoldId(0);
        ResponseEntity responseEntity = reservationService.reserveSeats(seatReservationDTO);
        assertTrue(responseEntity.getStatusCode().is4xxClientError());
        assertEquals("The details provided are not valid for this hold.", responseEntity.getBody());
    }

    @Test
    public void reserveSeatsWithCorrectIDTest() {
        seatHoldDTO.setNumberOfSeats(5);
        ResponseEntity<SeatHold> responseHoldConfirmation = reservationService.findAndHoldSeats(seatHoldDTO);

        seatReservationDTO.setSeatHoldId(responseHoldConfirmation.getBody().getSeatHoldId());

        ResponseEntity<SeatHold> responseReservationConfirmation = reservationService.reserveSeats(seatReservationDTO);
        assertTrue(responseReservationConfirmation.getStatusCode().is2xxSuccessful());
        assertNotNull(responseReservationConfirmation.getBody().getConfirmationCode());
        assertEquals("firstName", responseReservationConfirmation.getBody().getFirstName());
        assertEquals("lastName", responseReservationConfirmation.getBody().getLastName());
        assertEquals("abc@gmail.com", responseReservationConfirmation.getBody().getCustomerEmail());
    }

    @Test
    public void reserveSeatsWithWrongUserTest() {
        seatHoldDTO.setNumberOfSeats(5);
        ResponseEntity<SeatHold> responseHoldConfirmation = reservationService.findAndHoldSeats(seatHoldDTO);
        seatReservationDTO.setSeatHoldId(responseHoldConfirmation.getBody().getSeatHoldId());
        seatReservationDTO.setCustomerEmail("xyz@gmail.com");
        seatReservationDTO.setFirstName("lastName");
        seatReservationDTO.setLastName("firstName");
        ResponseEntity<SeatHold> responseReservationConfirmation = reservationService.reserveSeats(seatReservationDTO);
        assertTrue(responseReservationConfirmation.getStatusCode().is4xxClientError());
        assertEquals("The details provided are not valid for this hold.", responseReservationConfirmation.getBody());
    }
}
