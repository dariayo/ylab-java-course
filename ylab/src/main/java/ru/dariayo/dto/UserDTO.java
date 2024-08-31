package ru.dariayo.dto;

import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotNull(message = "Username cannot be null")
        String name,
        @NotNull(message = "Role cannot be null")
        String role,
        @NotNull(message = "Count cannot be null")
        Integer countOrders,
        @NotNull(message = "Contacts cannot be null")
        String contacts,
        @NotNull(message = "Password cannot be null")
        String password) {
}
