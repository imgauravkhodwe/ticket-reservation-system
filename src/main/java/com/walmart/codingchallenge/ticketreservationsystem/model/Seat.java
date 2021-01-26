package com.walmart.codingchallenge.ticketreservationsystem.model;

import com.walmart.codingchallenge.ticketreservationsystem.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private int row;
    private int column;
    private String status;

    public boolean isFree() {
        return this.status.equals(Constants.STATUS_FREE);
    }

}
