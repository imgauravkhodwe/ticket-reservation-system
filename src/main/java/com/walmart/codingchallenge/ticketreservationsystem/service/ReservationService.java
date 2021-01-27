/**
 * @author Gaurav Khodwe
 */

package com.walmart.codingchallenge.ticketreservationsystem.service;

import com.walmart.codingchallenge.ticketreservationsystem.model.*;
import com.walmart.codingchallenge.ticketreservationsystem.util.Constants;
import com.walmart.codingchallenge.ticketreservationsystem.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {
    public Map<String, SeatHold> holdMap = new HashMap<>();
    @Autowired
    Venue venue;

    @Autowired
    Utils utils;

    @Autowired
    public ReservationService() {
    }

    /**
     * Get total number of FREE seats in the venue
     */
    private int getAvailableSeats() {
        return venue.getAvailSeats();
    }

    /**
     * Get the map which stores the seats in HOLD status
     */
    public Map<String, SeatHold> getHoldMap() {
        return this.holdMap;
    }

    /**
     * Find and hold seat seat for the user.
     * Validate the incoming DTO to have valid requested number and seats,
     * the request number of seats should not be greater than current available seats.
     *
     * @param seatHoldDTO
     * @return responseEntity
     */
    public ResponseEntity findAndHoldSeats(SeatHoldDTO seatHoldDTO) {

        utils.clearExpiredHolds();

        if (seatHoldDTO.getNumberOfSeats() <= 0) {
            return new ResponseEntity("You must hold at least 1 seat.", HttpStatus.BAD_REQUEST);
        }

        if (seatHoldDTO.getNumberOfSeats() > getAvailableSeats()) {
            return new ResponseEntity("Not enough seats are left to fulfill your order.", HttpStatus.ACCEPTED);
        }

        Set<Seat> seatOrder = findBestSeats(seatHoldDTO.getNumberOfSeats());
        SeatHold seatHold = new SeatHold(seatHoldDTO.getFirstName(), seatHoldDTO.getLastName(), seatHoldDTO.getCustomerEmail(), seatOrder);
        holdMap.put(seatHold.getSeatHoldId(), seatHold);
        return new ResponseEntity(seatHold, HttpStatus.OK);
    }

    /**
     * Find the seat placed on hold by the user and book them for the user.
     * Validate the incoming DTO to have valid seatHold, correct user details and the experation timmer on the seats.
     *
     * @param seatReservationDTO
     * @return responseEntity
     */
    public ResponseEntity reserveSeats(SeatReservationDTO seatReservationDTO) {
        SeatHold seatHold = holdMap.get(seatReservationDTO.getSeatHoldId());

        if (seatHold == null || seatReservationDTO.getCustomerEmail().isEmpty() || !isValidCustomer(seatHold, seatReservationDTO)) {
            return new ResponseEntity("The details provided are not valid for this hold.", HttpStatus.BAD_REQUEST);
        }

        if (seatHold.getExpirationTimer() < System.currentTimeMillis()) {
            return new ResponseEntity("Your hold reservation has expired. Please place a new hold.", HttpStatus.BAD_REQUEST);
        }

        Iterator<Seat> iterator = seatHold.getSeats().iterator();

        while (iterator.hasNext()) {
            Seat seatId = iterator.next();
            if (venue.getSeat(seatId.getRow(), seatId.getColumn()).getStatus().equals(Constants.STATUS_ON_HOLD)) {
                venue.getSeat(seatId.getRow(), seatId.getColumn()).setStatus(Constants.STATUS_BOOKED);
            }
        }
        UUID uuid = UUID.randomUUID();
        seatHold.setConfirmationCode(uuid.toString());
        seatHold.setExpirationTimer(0L);
        return new ResponseEntity(seatHold, HttpStatus.OK);
    }


    /*
     * Helper function to verify the user is valid and seats can be booked
     **/
    private boolean isValidCustomer(SeatHold seatHold, SeatReservationDTO seatReservationDTO) {
        return seatHold.getCustomerEmail().equalsIgnoreCase(seatReservationDTO.getCustomerEmail()) &&
                seatHold.getFirstName().equalsIgnoreCase(seatReservationDTO.getFirstName()) &&
                seatHold.getLastName().equalsIgnoreCase(seatReservationDTO.getLastName());
    }

    /**
     * Find and hold the best seats which are considered to be closer to the stage and
     * filled left to right keep the group of all user toger in one rows.
     * If the above is not possible, it will place the user closer to the stage on the vaccant seat
     * from left to right, in this case the user groups may be split in two rows.
     * closer together.
     *
     * @param The number of seats to find.
     * @return Set of seats which are placed on for the user.
     */
    private Set<Seat> findBestSeats(int numSeats) {
        Set<Seat> seatFinder = new HashSet<Seat>();
        for (int row = 0; row < venue.getRowSize(); row++) {
            if (venue.getAvailableSeatsInRow(row) >= numSeats) {
                List<Seat> newReservation = new ArrayList<>();
                for (int i = 0; i < numSeats; i++) {
                    newReservation.add(new Seat(row, venue.getSeatLayout().get(row).size() + i, Constants.STATUS_ON_HOLD));
                }
                numSeats = 0;
                venue.getSeatLayout().get(row).addAll(newReservation);
                seatFinder.addAll(newReservation);
                break;
            }
        }
        if (numSeats != 0) {
            for (int row = 0; row < venue.getNumRows() && numSeats > 0; row++) {
                if (venue.getAvailableSeatsInRow(row) < venue.getNumColumns()) {
                    for (int i = venue.getColumnSize(row); i < venue.getNumColumns() && numSeats > 0; i++) {
                        Seat newReservation = new Seat(row, i, Constants.STATUS_ON_HOLD);
                        numSeats--;
                        venue.getSeatLayout().get(row).add(newReservation);
                        seatFinder.add(newReservation);
                    }
                }
            }
        }
        return seatFinder;
    }
}
