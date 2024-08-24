package ru.dariayo.dto;

public record UserDTO(
        String name,
        String role,
        Integer countOrders,
        String contacts,
        String password) {
}
