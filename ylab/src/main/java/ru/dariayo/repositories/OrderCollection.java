package ru.dariayo.repositories;

import java.util.Scanner;
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

    public void searchOrder(String param) {
        TreeSet<Order> orders = new TreeSet<>();
        System.out.println("Введите параметр поиска");
        Scanner scanner = new Scanner(System.in);
        String arg = scanner.nextLine();
        switch (param) {
            case "client":
                for (Order order : orderCollection) {
                    if (order.getNameBuyer().equals(arg)) {
                        orders.add(order);
                    }
                }
                break;
            case "status":
                for (Order order : orderCollection) {
                    if (order.getStatus().equals(arg)) {
                        orders.add(order);
                    }
                }
                break;
            case "car":
                for (Order order : orderCollection) {
                    if (order.getCar().equals(arg)) {
                        orders.add(order);
                    }
                }
                break;
            default:
                System.out.println("Invalid sort parameter.");
                return;
        }

        for (Order order : orders) {
            System.out.println(order.getNameBuyer() + ", " + order.getCar() + ", " + order.getStatus());
        }
    }

}
