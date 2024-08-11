package ru.dariayo.repositories;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.dariayo.db.DBManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Car;

public class CarCollection {
    private static final Logger logger = Logger.getLogger(PersonCollection.class.getName());
    private AuditLogRepository auditLogRepository;
    private DBManager dbManager;

    public CarCollection() {
    }

    public CarCollection(AuditLogRepository auditLogRepository, DBManager dbManager) {
        this.auditLogRepository = auditLogRepository;
        this.dbManager = dbManager;
    }

    /**
     * information about all cars
     */
    public void showCars() {
        dbManager.showCars();
    }

    /**
     * add new car to treeset
     * 
     * @param car
     * @throws SQLException
     */
    public void addCar(Car car) throws SQLException {
        dbManager.addCar(car);
        logger.log(Level.INFO, "Add car: " + car.getMark());
        auditLogRepository.logAction("System", "Add car", "Mark: " + car.getMark() + " Model: " + car.getModel());
    }

    /**
     * remove car
     * 
     * @param mark
     * @param model
     */
    public void removeCar(String mark, String model) {
        dbManager.removeCar(mark, model);
        logger.log(Level.INFO, "Remove car: " + mark);
        auditLogRepository.logAction("System", "Remove car",
                "Mark: " + mark + " Model: " + model);
        System.out.println("Автомобиль удален");
    }

    /**
     * update params of car
     * 
     * @param mark
     * @param model
     */
    public void updateCar(String mark, String model) {
        dbManager.updateCar(mark, model);
    }

    /**
     * get car by mark
     * 
     * @param mark
     * @param model
     * @return
     */
    public Car getByMark(String mark, String model) {
        return dbManager.searchCar(mark, model);
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
        cars.add(dbManager.searchCarByParam(arg, param));
        for (Car car : cars) {
            System.out.println(car.getMark() + ", " + car.getModel() + ", " + car.getPrice());
        }
    }

}
