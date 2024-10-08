package ru.dariayo.model;

public class Car implements Comparable<Car> {
    private String mark;
    private String model;
    private int yearOfIssue;
    private int price;
    private String condition;

    public Car(String mark, String model, int yearOfIssue, int price, String condition) {
        this.mark = mark;
        this.model = model;
        this.yearOfIssue = yearOfIssue;
        this.price = price;
        this.condition = condition;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public int compareTo(Car o) {
        int orderComparison = Integer.compare(this.price, o.getPrice());
        if (orderComparison != 0) {
            return orderComparison;
        }
        return this.model.compareTo(o.getModel());
    }
}
