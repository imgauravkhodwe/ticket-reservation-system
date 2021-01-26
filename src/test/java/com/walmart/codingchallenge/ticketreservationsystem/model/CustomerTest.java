package com.walmart.codingchallenge.ticketreservationsystem.model;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.AllOf.*;

import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    public void customerTest() {
        assertThat(Customer.class, allOf(hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCode(),
                hasValidBeanEquals(),
                hasValidBeanToString()));
    }
}
