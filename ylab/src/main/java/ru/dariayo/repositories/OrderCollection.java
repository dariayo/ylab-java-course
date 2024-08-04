package ru.dariayo.repositories;

import java.util.TreeSet;

import ru.dariayo.model.Order;
import ru.dariayo.model.Person;

public class OrderCollection {
    private TreeSet<Order> orderCollection = new TreeSet<>();

    public OrderCollection(TreeSet<Order> orderCollection) {
        this.orderCollection = orderCollection;
    }

    public OrderCollection() {
    }

    public void makeOrder(Person person, String mark, String model, CarCollection carCollection) {
        Order order = new Order(person.getName(), "Placed", carCollection.getByMark(mark, model));
        orderCollection.add(order);
        System.out.println(order.getCar().getPrice());
    }

    public void changeStatus(int id, String status) {
        for (Order order : orderCollection) {
            if (order.getId() == id) {
                order.setStatus(status);
            }
        }
    }

    public void getOrder(int id) {
        for (Order order : orderCollection) {
            if (order.getId() == id) {
                System.out.println("Покупатель: " + order.getNameBuyer() + " Статус заказа: " + order.getStatus()
                        + " Автомобиль: " + order.getCar().getModel());
            }
        }
    }
}
