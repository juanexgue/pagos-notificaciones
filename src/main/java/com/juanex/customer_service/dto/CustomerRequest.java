package com.juanex.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(

        @NotBlank(message = "Name is required")
        String name,

        @Email(message = "Invalid email")
        String email

) {
}
