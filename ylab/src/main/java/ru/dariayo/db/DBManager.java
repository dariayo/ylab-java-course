package ru.dariayo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.dariayo.model.Car;
import ru.dariayo.model.Order;
import ru.dariayo.model.Person;

public class DBManager {
    private static final String TABLE_USER = "users";
    private static final String TABLE_CAR = "cars";
    private static final String TABLE_ORDER = "orders";
    private static final String CONTACTS = "contacts";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final String ROLE = "role";
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    private static final String SQL_FIND_USERNAME = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", TABLE_USER,
            CONTACTS);
    private static final String SQL_ADD_USER = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES (?, ?, ?, ?)",
            TABLE_USER, USERNAME, PASSWORD, ROLE, CONTACTS);
    private static final String SQL_ADD_CAR = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?, ?, ?, ?, ?)",
            TABLE_CAR, "mark", "model", "year", "price", "condition");
    private static final String SQL_ADD_ORDER = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES (?, ?, ?, ?)",
            TABLE_ORDER, "nameBuyer", "status", "mark", "model");

    private static final String SQL_CREATE_PERSON = String.format(
            "DROP TABLE IF EXISTS %s; CREATE TABLE %s (id SERIAL PRIMARY KEY, username TEXT, password TEXT, role TEXT, contacts TEXT);",
            TABLE_USER, TABLE_USER);

    private static final String SQL_CREATE_CAR = String.format(
            "DROP TABLE IF EXISTS %s; CREATE TABLE %s (id SERIAL PRIMARY KEY, mark TEXT, model TEXT, year INTEGER, price INTEGER, condition TEXT);",
            TABLE_CAR, TABLE_CAR);

    private static final String SQL_CREATE_ORDER = String.format(
            "DROP TABLE IF EXISTS %s; CREATE TABLE %s (id SERIAL PRIMARY KEY, nameBuyer TEXT, status TEXT, mark TEXT, model TEXT);",
            TABLE_ORDER, TABLE_ORDER);

    public DBManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

    }

    public void connectDB() {

        // try {
        //     connection = DriverManager.getConnection(url, username, password);

        // } catch (SQLException e) {

        // }
    }

    public void registerUser(Person person) throws SQLException {
        // if (!userExist(person)) {
        //     PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER);
        //     statement.setString(1, person.getName());
        //     statement.setString(2, person.getPassword());
        //     statement.setString(3, person.getRole());
        //     statement.setString(4, person.getContacts());
        //     statement.executeUpdate();
        //     statement.close();
        // }

    }

    public boolean userExist(Person person) throws SQLException {
        // String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", TABLE_USER, CONTACTS);
        // PreparedStatement statement = connection.prepareStatement(sql);
        // statement.setString(1, person.getContacts());
        // ResultSet resultSet = statement.executeQuery();
        // resultSet.next();
        // int count = resultSet.getInt(1);
        // statement.close();
        // return count != 0;
        return true;
    }

    public void createTablePerson() throws SQLException {
        // PreparedStatement statement = connection.prepareStatement(SQL_CREATE_PERSON);
        // statement.executeUpdate();
        // statement.close();
    }

    public void createTableCars() throws SQLException {
        // PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CAR);
        // statement.executeUpdate();
        // statement.close();
    }

    public void createTableOrders() throws SQLException {
        // PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ORDER);
        // statement.executeUpdate();
        // statement.close();
    }

    public void addCar(Car car) throws SQLException {
        // PreparedStatement statement = connection.prepareStatement(SQL_ADD_CAR);
        // statement.setString(1, car.getMark());
        // statement.setString(2, car.getModel());
        // statement.setInt(3, car.getPrice());
        // statement.setInt(4, car.getYearOfIssue());
        // statement.setString(5, car.getCondition());
        // statement.executeUpdate();
        // statement.close();
    }

    public void addOrder(Order order) throws SQLException {
        // PreparedStatement statement = connection.prepareStatement(SQL_ADD_ORDER);
        // statement.setString(1, order.getNameBuyer());
        // statement.setString(2, order.getStatus());
        // statement.setString(3, order.getCar().getMark());
        // statement.setString(4, order.getCar().getMark());
        // statement.executeUpdate();
        // statement.close();
    }

}
