package ru.dariayo.dto;

public record OrderDTO(
                Integer currentId,
                Integer id,
                String nameBuyer,
                String status,
                String car) {

        public Integer currentId() {
                return currentId;
        }

        public Integer id() {
                return id;
        }

        public String nameBuyer() {
                return nameBuyer;
        }

        public String status() {
                return status;
        }

        public String car() {
                return car;
        }

}
