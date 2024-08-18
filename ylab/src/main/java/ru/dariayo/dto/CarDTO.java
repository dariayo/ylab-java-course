package ru.dariayo.dto;

public record CarDTO(
        String mark,
        String model,
        Integer yearOfIssue,
        Integer price,
        String condition) {

}
