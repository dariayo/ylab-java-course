package ru.dariayo.repositories;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Order;
import ru.dariayo.model.Person;

public class OrderCollection {
    private TreeSet<Order> orderCollection = new TreeSet<>();
    private static final Logger logger = Logger.getLogger(PersonCollection.class.getName());
    private AuditLogRepository auditLogRepository;

    public OrderCollection(TreeSet<Order> orderCollection) {
        this.orderCollection = orderCollection;
    }

    public OrderCollection(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * add order to treeset
     * 
     * @param person
     * @param mark
     * @param model
     * @param carCollection
     */
    public void makeOrder(Person person, String mark, String model, CarCollection carCollection) {
        Order order = new Order(person.getName(), "Placed", carCollection.getByMark(mark, model));
        orderCollection.add(order);
        System.out.println(order.getCar().getPrice());
        logger.log(Level.INFO, "Create order: " + order.getId());
        auditLogRepository.logAction("System", "Create order",
                "Number: " + order.getId() + " By user: " + order.getNameBuyer());
    }

    /**
     * change status of order
     * 
     * @param id
     * @param status
     */
    public void changeStatus(int id, String status) {
        for (Order order : orderCollection) {
            if (order.getId() == id) {
                order.setStatus(status);
                logger.log(Level.INFO, "Update order: " + order.getId());
                auditLogRepository.logAction("System", "Update order",
                        "Number: " + order.getId() + " New status: " + order.getStatus());
            }
        }
    }

    /**
     * get order by id
     */
    public void getOrder(int id) {
        for (Order order : orderCollection) {
            if (order.getId() == id) {
                System.out.println("Покупатель: " + order.getNameBuyer() + " Статус заказа: " + order.getStatus()
                        + " Автомобиль: " + order.getCar().getModel());
            }
        }
    }

    /**
     * search order by param (name of client, status, car)
     * 
     * @param param
     */
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
