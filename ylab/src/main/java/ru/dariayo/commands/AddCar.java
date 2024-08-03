package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.model.Car;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.PersonCollection;

public class AddCar extends Command {
    private final CarCollection carCollection;
    private final PersonCollection personCollection;

    public AddCar(CarCollection carCollection, PersonCollection personCollection) {
        this.carCollection = carCollection;
        this.personCollection = personCollection;
    }

    @Override
    public void execute() {
        System.out.println(personCollection.getPerson().getRole());
        if (personCollection.getPerson().getRole().equals("admin")
                || personCollection.getPerson().getRole().equals("manager")) {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Введите марку автомобиля: ");
                String mark = scanner.nextLine();
                System.out.println("Введите модель автомобиля: ");
                String model = scanner.nextLine();
                System.out.println("Введите год выпуска автомобиля: ");
                // TODO
                int year = Integer.parseInt(scanner.nextLine());
                System.out.println("Введите цену автомобиля: ");
                // TODO
                int price = Integer.parseInt(scanner.nextLine());
                System.out.println("Введите состояние автомобиля: ");
                String condition = scanner.nextLine();
                Car car = new Car(mark, model, year, price, condition);
                carCollection.addCar(car);
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return "add_car";
    }

}
