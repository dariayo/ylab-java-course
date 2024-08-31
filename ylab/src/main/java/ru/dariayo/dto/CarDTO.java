package ru.dariayo.dto;

import jakarta.validation.constraints.NotNull;

public record CarDTO(
                @NotNull(message = "Mark cannot be null") String mark,
                @NotNull(message = "Model cannot be null") String model,
                @NotNull(message = "Year cannot be null") Integer yearOfIssue,
                @NotNull(message = "Price cannot be null") Integer price,
                @NotNull(message = "Condition cannot be null") String condition) {

}
