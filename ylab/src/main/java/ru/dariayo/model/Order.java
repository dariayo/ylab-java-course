package ru.dariayo.model;

public class Order implements Comparable<Order> {
    private int id;
    private String nameBuyer;
    private String status;
    private Car car;

    public Order(int id, String nameBuyer, String status, Car car) {
        this.id = id;
        this.nameBuyer = nameBuyer;
        this.status = status;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameBuyer() {
        return nameBuyer;
    }

    public void setNameBuyer(String nameBuyer) {
        this.nameBuyer = nameBuyer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(this.id - o.getId(), 0);
    }

}
