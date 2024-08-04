package ru.dariayo.repositories;

import java.util.Scanner;
import java.util.TreeSet;

import ru.dariayo.comparator.PersonContactsComparator;
import ru.dariayo.comparator.PersonNameComparator;
import ru.dariayo.comparator.PersonOrdersComparator;
import ru.dariayo.model.Car;
import ru.dariayo.model.Person;

public class CarCollection {
    private TreeSet<Car> carCollection = new TreeSet<>();

    public CarCollection(TreeSet<Car> carCollection) {
        this.carCollection = carCollection;
    }

    public CarCollection() {
    }

    public void showCars() {
        Car car1 = new Car("null", "null", 0, 0, null);
        carCollection.add(car1);
        for (Car car : carCollection) {
            System.out.println("Марка: " + car.getMark() + " модель: " + car.getModel() + " год выпуска: "
                    + car.getYearOfIssue() + " цена: " + car.getPrice() + " состояние: " + car.getCondition());
        }
    }

    public void addCar(Car car) {
        carCollection.add(car);
    }

    public void removeCar(String mark, String model) {
        for (Car car : carCollection) {
            if (car.getModel().equals(model) && car.getMark().equals(mark)) {
                carCollection.remove(car);
                System.out.println("Автомобиль удален");
            }
        }
    }

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
                }
            }
        }
    }

    public Car getByMark(String mark, String model) {
        for (Car car : carCollection) {
            if (car.getMark().equals(mark) && car.getModel().equals(model)) {
                return car;
            }
        }
        return null;
    }

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
