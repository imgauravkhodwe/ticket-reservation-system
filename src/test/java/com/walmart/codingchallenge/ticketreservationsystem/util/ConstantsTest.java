/**
 * @author Gaurav Khodwe
 */

package com.walmart.codingchallenge.ticketreservationsystem.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantsTest {

    @Test
    public void holdStatusTest() {
        assertEquals("HOLD", Constants.STATUS_ON_HOLD);
    }

    @Test
    public void bookedStatusTest() {
        assertEquals("BOOKED", Constants.STATUS_BOOKED);
    }

    @Test
    public void freeStatusTest() {
        assertEquals("FREE", Constants.STATUS_FREE);
    }

}
