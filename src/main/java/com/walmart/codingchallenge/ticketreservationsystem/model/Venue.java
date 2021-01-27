/**
 * @author Gaurav Khodwe
 */

package com.walmart.codingchallenge.ticketreservationsystem.model;

import com.walmart.codingchallenge.ticketreservationsystem.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Venue {
    private final static int numRows = 10;
    private final static int numColumns = 50;
    private final static int venueSize = numColumns * numRows;
    private final Map<Integer, List<Seat>> seats = new HashMap<>();

    @Autowired
    Utils utils;


    /**
     * Initializes with a fixed number of rows and columns.
     */
    public Venue() {
        buildVenue();
    }

    public void buildVenue() {
        for (int row = 0; row < numRows; row++) {
            seats.put(row, new ArrayList<>());
        }
    }

    public int getAvailSeats() {
        utils.clearExpiredHolds();
        int availSeats = 0;
        for (int row = 0; row < getRowSize(); row++) {
            if (seats.get(row).size() < numColumns)
                availSeats = availSeats + this.getAvailableSeatsInRow(row);
        }
        return availSeats;
    }

    public Seat getSeat(int a, int b) {
        return seats.get(a).get(b);
    }

    public int getRowSize() {
        return seats.size();
    }

    public int getColumnSize(int row) {
        return seats.get(row).size();
    }

    public int getVenueSize() {
        return venueSize;
    }

    public Map<Integer, List<Seat>> getSeatLayout() {
        return seats;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getAvailableSeatsInRow(int row) {
        return (numColumns - seats.get(row).size());
    }
}
