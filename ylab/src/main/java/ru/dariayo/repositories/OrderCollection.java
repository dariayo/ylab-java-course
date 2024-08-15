package ru.dariayo.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.dariayo.db.DBManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Order;
import ru.dariayo.model.Person;

public class OrderCollection {
    private static final String TABLE_ORDER = "cs_schema.orders";
    private static final Logger logger = Logger.getLogger(PersonCollection.class.getName());
    private AuditLogRepository auditLogRepository;
    private Connection connection;

    private static final String SQL_ADD_ORDER = String.format("INSERT INTO %s (%s,%s,%s) VALUES (?, ?, ?)",
            TABLE_ORDER, "nameBuyer", "status", "mark");

    public OrderCollection() {
    }

    public OrderCollection(AuditLogRepository auditLogRepository, DBManager dbManager) {
        this.auditLogRepository = auditLogRepository;
        this.connection = dbManager.connectDB();
    }

    /**
     * add order to treeset
     * 
     * @param person
     * @param mark
     * @param model
     * @param carCollection
     * @throws SQLException
     */
    public void makeOrder(Person person, String mark, String model, CarCollection carCollection) throws SQLException {
        Order order = new Order(person.getName(), "Placed", mark);
        PreparedStatement statement = connection.prepareStatement(SQL_ADD_ORDER);
        statement.setString(1, order.getNameBuyer());
        statement.setString(2, order.getStatus());
        statement.setString(3, order.getCar());
        statement.executeUpdate();
        statement.close();
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
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cs_schema.orders SET status = ?" +
                        " WHERE id = ? ")) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.execute();
        } catch (Exception e) {
        }
    }

    /**
     * get order by id
     */
    public void getOrder(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM cs_schema.orders WHERE id = ? ")) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Покупатель: " + resultSet.getString("nameBuyer") + " Статус заказа: "
                            + resultSet.getString("status")
                            + " Автомобиль: " + resultSet.getString("mark"));
                }
            }
        } catch (Exception e) {
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
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM cs_schema.orders WHERE ? = ? ")) {
            statement.setString(1, param);
            statement.setString(2, arg);
            statement.execute();
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = new Order(
                            resultSet.getString("nameBuyer"),
                            resultSet.getString("status"),
                            resultSet.getString("mark"));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
        }
        for (Order order : orders) {
            System.out.println(order.getNameBuyer() + ", " + order.getCar() + ", " + order.getStatus());
        }
    }

}
