/**
 * @author Gaurav Khodwe
 */

package com.walmart.codingchallenge.ticketreservationsystem.model;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

public class SeatHoldDTOTest {
    @Test
    public void seatHoldDTOTest() {
        assertThat(SeatHoldDTOTest.class, allOf(hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCode(),
                hasValidBeanEquals(),
                hasValidBeanToString()));
    }
}
