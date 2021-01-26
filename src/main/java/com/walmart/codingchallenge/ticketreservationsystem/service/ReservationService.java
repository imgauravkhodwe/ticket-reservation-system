package com.walmart.codingchallenge.ticketreservationsystem.service;

import com.walmart.codingchallenge.ticketreservationsystem.model.*;
import com.walmart.codingchallenge.ticketreservationsystem.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {
    public Map<Integer, SeatHold> holdMap = new HashMap<Integer, SeatHold>();
    @Autowired
    Venue venue;

    @Autowired
    public ReservationService() {
    }

    private int getAvailableSeats() {
        return venue.getAvailSeats();
    }

    /**
     * Checks to ensure at least one seat is looking to be held and that enough seats are
     * available within the venue. It then puts a hold on the best seats that it finds. Also
     * calls the function to clear expired seat holds to potentially open up more seats for the customer.
     *
     * @param seatHoldDTO
     */
    public ResponseEntity findAndHoldSeats(SeatHoldDTO seatHoldDTO) {

        clearExpiredHolds();

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
     * This function is use to set the status of a seat to reserved or to available if the
     * hold time has expired. It provides the user with an Confirmation Code upon successful
     * reservation or a message if the hold has expired. It also throws an Exception if provided
     * with an email that does not match the email tied to the seatHoldId.
     */
    public ResponseEntity reserveSeats(SeatReservationDTO seatReservationDTO) {
        SeatHold seatHold = holdMap.get(seatReservationDTO.getSeatHoldId());

        if (seatHold.isEmpty() || seatReservationDTO.getCustomerEmail().isEmpty() || !isValidCustomer(seatHold, seatReservationDTO)) {
            return new ResponseEntity("Your email does not match the email on file for this hold.", HttpStatus.BAD_REQUEST);
        }

        if (seatHold.getExpirationTimer() < System.currentTimeMillis()) {
            return new ResponseEntity("Your hold reservation has expired. Please place a new hold.", HttpStatus.BAD_REQUEST);
        }

        Iterator<Seat> iterator = seatHold.getSeats().iterator();
        int count = seatHold.getNumberOfSeats();
        int temp = count;

        while (iterator.hasNext()) {
            Seat seatId = iterator.next();
            for (int row = 0; row < venue.getRowSize(); row++) {
                for (int column = 0; column < venue.getColumnSize(); column++) {
                    if (venue.getSeat(row, column) == seatId) {
                        venue.getSeat(row, column).setStatus(Constants.STATUS_BOOKED);
                        count--;
                    }
                    if (count < temp) {
                        break;
                    }
                }
                if (count < temp) {
                    temp--;
                    break;
                }
            }
        }
        UUID uuid = UUID.randomUUID();
        seatHold.setConfirmationCode(uuid.toString());
        seatHold.setExpirationTimer(Long.valueOf(0));
        return new ResponseEntity(seatHold, HttpStatus.OK);
    }

    private boolean isValidCustomer(SeatHold seatHold, SeatReservationDTO seatReservationDTO) {
        return seatHold.getCustomerEmail().equalsIgnoreCase(seatReservationDTO.getCustomerEmail()) &&
                seatHold.getFirstName().equalsIgnoreCase(seatReservationDTO.getFirstName()) &&
                seatHold.getLastName().equalsIgnoreCase(seatReservationDTO.getLastName());
    }

    /**
     * A function used to switch expired held seats back to being available.
     */
    private void clearExpiredHolds() {
        Iterator<Integer> holdIterator = holdMap.keySet().iterator();
        while (holdIterator.hasNext()) {
            int holdId = holdIterator.next();
            SeatHold seatHold = holdMap.get(holdId);
            Iterator<Seat> seatIterator = seatHold.getSeats().iterator();
            while (seatIterator.hasNext()) {
                Seat seatId = seatIterator.next();
                for (int row = 0; row < venue.getRowSize(); row++) {
                    for (int column = 0; column < venue.getColumnSize(); column++) {
                        if (venue.getSeat(row, column) == seatId && venue.getSeat(row, column).getStatus().equals(Constants.STATUS_ON_HOLD)) {
                            if (seatHold.getExpirationTimer() < System.currentTimeMillis()) {
                                venue.getSeat(row, column).setStatus(Constants.STATUS_FREE);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * In this instance the best seats are considered to be closer to the stage and
     * the rows alternate the side from which they are filled in order to keep groups
     * closer together.
     *
     * @param numSeats The number of seats to find.
     */
    public Set<Seat> findBestSeats(int numSeats) {
        Set<Seat> seatFinder = new HashSet<Seat>();
        int count = numSeats;
        for (int row = 0; row < venue.getRowSize(); row++) {
            if (row % 2 == 0) {
                for (int column = 0; column < venue.getColumnSize(); column++) {
                    // check this
                    if (count == 0) {
                        break;
                    } else if (venue.getSeat(row, column).getStatus().equals(Constants.STATUS_FREE)) {
                        venue.getSeat(row, column).setStatus(Constants.STATUS_ON_HOLD);
                        count--;
                        seatFinder.add(venue.getSeat(row, column));
                    }
                }
            } else {
                for (int column = venue.getColumnSize() - 1; column >= 0; column--) {
                    // check this
                    if (count == 0) {
                        break;
                    } else if (venue.getSeat(row, column).getStatus().equals(Constants.STATUS_FREE)) {
                        venue.getSeat(row, column).setStatus(Constants.STATUS_ON_HOLD);
                        count--;
                        seatFinder.add(venue.getSeat(row, column));
                    }
                }
            }
        }
        return seatFinder;
    }
}
