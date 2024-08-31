package ru.dariayo.dto;

public class OrderRequestDTO {
    private String buyerName;
    private String carMark;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(String buyerName, String carMark) {
        this.buyerName = buyerName;
        this.carMark = carMark;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }
}
