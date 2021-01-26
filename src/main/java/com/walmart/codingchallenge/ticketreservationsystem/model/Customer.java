package com.walmart.codingchallenge.ticketreservationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @NotNull(message="First name cannot be null")
    @NotEmpty(message="First name cannot be Empty")
    @Size(min=1, message="First name cannot be blank")
    private String firstName;

    @NotNull(message="Last name cannot be null")
    @NotEmpty(message="Last name cannot be Empty")
    @Size(min=1, message="Last name cannot be blank")
    private String lastName;

    @Email
    @NotNull(message="Email cannot be null")
    @NotEmpty(message="Email cannot be Empty")
    @Size(min=1, message="Email cannot be blank")
    private String customerEmail;

}
