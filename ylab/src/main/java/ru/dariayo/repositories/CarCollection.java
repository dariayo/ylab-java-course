package ru.dariayo.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.dariayo.db.DBManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Car;

public class CarCollection {
    private static final String TABLE_CAR = "cs_schema.cars";
    private static final Logger logger = Logger.getLogger(PersonCollection.class.getName());
    private AuditLogRepository auditLogRepository;
    private Connection connection;
    private static final String SQL_ADD_CAR = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?, ?, ?, ?, ?)",
            TABLE_CAR, "mark", "model", "year", "price", "condition");

    public CarCollection() {
    }

    public CarCollection(AuditLogRepository auditLogRepository, DBManager dbManager) {
        this.auditLogRepository = auditLogRepository;
        this.connection = dbManager.connectDB();
    }

    /**
     * information about all cars
     */
    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cs_schema.cars";
        
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                Car car = new Car(
                    resultSet.getString("mark"),
                    resultSet.getString("model"),
                    resultSet.getInt("year"),
                    resultSet.getInt("price"),
                    resultSet.getString("condition")
                );
                cars.add(car);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Логирование ошибки для отладки
        }
        return cars;
    }

    /**
     * add new car to treeset
     * 
     * @param car
     * @throws SQLException
     */
    public void addCar(Car car) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_ADD_CAR);
        statement.setString(1, car.getMark());
        statement.setString(2, car.getModel());
        statement.setInt(3, car.getPrice());
        statement.setInt(4, car.getYearOfIssue());
        statement.setString(5, car.getCondition());
        statement.executeUpdate();
        statement.close();
        logger.log(Level.INFO, "Add car: " + car.getMark());
        auditLogRepository.logAction("System", "Add car", "Mark: " + car.getMark() + " Model: " + car.getModel());
    }

    /**
     * remove car
     * 
     * @param mark
     * @param model
     */
    public boolean removeCar(String mark, String model) {
        try (PreparedStatement statement = connection
                .prepareStatement("DELETE FROM cs_schema.cars WHERE mark = ? AND model = ?")) {
            statement.setString(1, mark);
            statement.setString(2, model);
            statement.execute();
        } catch (Exception e) {
           return false;
        }
        logger.log(Level.INFO, "Remove car: " + mark);
        auditLogRepository.logAction("System", "Remove car",
                "Mark: " + mark + " Model: " + model);
        System.out.println("Автомобиль удален");
        return true;
    }

    /**
     * update params of car
     * 
     * @param mark
     * @param model
     */
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

    /**
     * get car by mark
     * 
     * @param mark
     * @param model
     * @return
     */
    public Car getByMark(String mark, String model) {
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

    /**
     * search car by param
     * 
     * @param param
     */
    public void searchCar(String param) {
        TreeSet<Car> cars = new TreeSet<>();
        System.out.println("Введите параметр поиска");
        Scanner scanner = new Scanner(System.in);
        String arg = scanner.nextLine();
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
                    cars.add(car);
                }
            }
        } catch (Exception e) {
        }
        for (Car car : cars) {
            System.out.println(car.getMark() + ", " + car.getModel() + ", " + car.getPrice());
        }
    }

}
