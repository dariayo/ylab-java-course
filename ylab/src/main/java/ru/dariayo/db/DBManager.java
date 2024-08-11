package ru.dariayo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

import ru.dariayo.model.Car;
import ru.dariayo.model.Order;
import ru.dariayo.model.Person;

public class DBManager {
    private static final String TABLE_USER = "cs_schema.users";
    private static final String TABLE_CAR = "cs_schema.cars";
    private static final String TABLE_ORDER = "cs_schema.orders";
    private static final String CONTACTS = "contacts";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final String ROLE = "role";
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    private static final String SQL_ADD_USER = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES (?, ?, ?, ?)",
            TABLE_USER, USERNAME, PASSWORD, ROLE, CONTACTS);
    private static final String SQL_ADD_CAR = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?, ?, ?, ?, ?)",
            TABLE_CAR, "mark", "model", "year", "price", "condition");
    private static final String SQL_ADD_ORDER = String.format("INSERT INTO %s (%s,%s,%s) VALUES (?, ?, ?)",
            TABLE_ORDER, "nameBuyer", "status", "mark");

    public DBManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

    }

    public void connectDB() {

        try {
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {

        }
    }

    public void registerUser(Person person) throws SQLException {
        if (!userExist(person)) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER);
            statement.setString(1, person.getName());
            statement.setString(2, person.getPassword());
            statement.setString(3, person.getRole());
            statement.setString(4, person.getContacts());
            statement.executeUpdate();
            statement.close();
        }

    }

    public boolean userExist(Person person) throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", TABLE_USER, CONTACTS);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, person.getContacts());
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        statement.close();
        return count != 0;
    }

    public void addCar(Car car) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_ADD_CAR);
        statement.setString(1, car.getMark());
        statement.setString(2, car.getModel());
        statement.setInt(3, car.getPrice());
        statement.setInt(4, car.getYearOfIssue());
        statement.setString(5, car.getCondition());
        statement.executeUpdate();
        statement.close();
    }

    public void addOrder(Order order) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_ADD_ORDER);
        statement.setString(1, order.getNameBuyer());
        statement.setString(2, order.getStatus());
        statement.setString(3, order.getCar());
        statement.executeUpdate();
        statement.close();
    }

    public void removeCar(String mark, String model) {
        try (PreparedStatement statement = connection
                .prepareStatement("DELETE FROM cs_schema.cars WHERE mark = ? AND model = ?")) {
            statement.setString(1, mark);
            statement.setString(2, model);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCar(String mark, String model) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите марку автомобиля: ");
        String markNew = scanner.nextLine();
        System.out.println("Введите модель автомобиля: ");
        String modelNew = (scanner.nextLine());
        System.out.println("Введите год выпуска автомобиля: ");
        int year = (Integer.parseInt(scanner.nextLine()));
        System.out.println("Введите цену автомобиля: ");
        int price = (Integer.parseInt(scanner.nextLine()));
        System.out.println("Введите состояние автомобиля: ");
        String condition = (scanner.nextLine());
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cs_schema.cars SET mark = ?," +
                        " model = ?," +
                        " year= ?," +
                        " price = ?," +
                        " condition = ?," +
                        " WHERE mark = ? AND model = ?")) {
            statement.setString(1, markNew);
            statement.setString(2, modelNew);
            statement.setInt(3, year);
            statement.setInt(4, price);
            statement.setString(5, condition);
            statement.setString(6, mark);
            statement.setString(7, model);
            statement.execute();
        } catch (Exception e) {
        }
    }

    public Car searchCar(String mark, String model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM cs_schema.cars WHERE mark = ? and model = ?")) {
            statement.setString(1, mark);
            statement.setString(2, model);
            statement.execute();
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Car car = new Car(
                            resultSet.getString("mark"),
                            resultSet.getString("model"),
                            resultSet.getInt("year"),
                            resultSet.getInt("price"),
                            resultSet.getString("condition"));
                    return car;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Car searchCarByParam(String arg, String param) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM cs_schema.cars WHERE ? = ? ")) {
            statement.setString(1, param);
            statement.setString(2, arg);
            statement.execute();
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Car car = new Car(
                            resultSet.getString("mark"),
                            resultSet.getString("model"),
                            resultSet.getInt("year"),
                            resultSet.getInt("price"),
                            resultSet.getString("condition"));
                    return car;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void showCars() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.cars")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("Марка: " + resultSet.getString("mark") + " модель: "
                            + resultSet.getString("model") + " год выпуска: "
                            + resultSet.getInt("year") + " цена: " + resultSet.getInt("price") + " состояние: "
                            + resultSet.getString("condition"));
                }
            }
        } catch (Exception e) {
        }
    }

    public Order searchOrderByParam(String arg, String param) {
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
                    return order;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void getOrderById(int id) {
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

    public void changeStatusOrder(int id, String status) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE cs_schema.orders SET status = ?" +
                        " WHERE id = ? ")) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.execute();
        } catch (Exception e) {
        }
    }
}
