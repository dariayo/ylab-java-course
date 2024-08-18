package ru.dariayo.dto;

public record OrderDTO(
        Integer currentId,
        Integer id,
        String nameBuyer,
        String status,
        String car) {

}
