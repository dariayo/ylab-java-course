package ru.dariayo.repositories;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Car;

public class CarCollection {
    private TreeSet<Car> carCollection = new TreeSet<>();
    private static final Logger logger = Logger.getLogger(PersonCollection.class.getName());
    private AuditLogRepository auditLogRepository;

    public CarCollection(TreeSet<Car> carCollection) {
        this.carCollection = carCollection;
    }

    public CarCollection(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public CarCollection() {
        //TODO Auto-generated constructor stub
    }

    /**
     * information about all cars
     */
    public void showCars() {
        for (Car car : carCollection) {
            System.out.println("Марка: " + car.getMark() + " модель: " + car.getModel() + " год выпуска: "
                    + car.getYearOfIssue() + " цена: " + car.getPrice() + " состояние: " + car.getCondition());
        }
    }

    /**
     * add new car to treeset
     * 
     * @param car
     */
    public void addCar(Car car) {
        carCollection.add(car);
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
        for (Car car : carCollection) {
            if (car.getModel().equals(model) && car.getMark().equals(mark)) {
                logger.log(Level.INFO, "Remove car: " + car.getMark());
                auditLogRepository.logAction("System", "Remove car",
                        "Mark: " + car.getMark() + " Model: " + car.getModel());
                carCollection.remove(car);
                System.out.println("Автомобиль удален");
            }
        }
    }

    /**
     * update params of car
     * 
     * @param mark
     * @param model
     */
    public void updateCar(String mark, String model) {
        for (Car car : carCollection) {
            if (car.getModel().equals(model) && car.getMark().equals(mark)) {
                try (Scanner scanner = new Scanner(System.in)) {
                    System.out.println("Введите марку автомобиля: ");
                    car.setMark(scanner.nextLine());
                    System.out.println("Введите модель автомобиля: ");
                    car.setModel(scanner.nextLine());
                    System.out.println("Введите год выпуска автомобиля: ");
                    car.setYearOfIssue(Integer.parseInt(scanner.nextLine()));
                    System.out.println("Введите цену автомобиля: ");
                    car.setPrice(Integer.parseInt(scanner.nextLine()));
                    System.out.println("Введите состояние автомобиля: ");
                    car.setCondition(scanner.nextLine());
                    logger.log(Level.INFO, "Update car: " + car.getMark());
                    auditLogRepository.logAction("System", "Update car",
                            "Mark: " + car.getMark() + " Model: " + car.getModel());
                }
            }
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
        for (Car car : carCollection) {
            if (car.getMark().equals(mark) && car.getModel().equals(model)) {
                return car;
            }
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
        switch (param) {
            case "mark":
                for (Car car : carCollection) {
                    if (car.getMark().equals(arg)) {
                        cars.add(car);
                    }
                }
                break;
            case "model":
                for (Car car : carCollection) {
                    if (car.getModel().equals(arg)) {
                        cars.add(car);
                    }
                }
                break;
            case "price":
                for (Car car : carCollection) {
                    if (car.getPrice() == Integer.parseInt(arg)) {
                        cars.add(car);
                    }
                }
                break;
            case "year":
                for (Car car : carCollection) {
                    if (car.getYearOfIssue() == Integer.parseInt(arg)) {
                        cars.add(car);
                    }
                }
                break;
            default:
                System.out.println("Invalid sort parameter.");
                return;
        }

        for (Car car : cars) {
            System.out.println(car.getMark() + ", " + car.getModel() + ", " + car.getPrice());
        }
    }

}
