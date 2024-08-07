package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.model.Car;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;
import ru.dariayo.userInterface.UserInterface;

public class AddCar extends Command {
    private final CarCollection carCollection;
    private final PersonCollection personCollection;
    private final ConsoleUserInterface userInterface;

    public AddCar(CarCollection carCollection, PersonCollection personCollection, ConsoleUserInterface userInterface) {
        this.carCollection = carCollection;
        this.personCollection = personCollection;
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        System.out.println(personCollection.getPerson().getRole());
        if (personCollection.getPerson().getRole().equals("admin")
                || personCollection.getPerson().getRole().equals("manager")) {
            try {
                String mark = userInterface.getInput("Введите марку автомобиля: ");
                String model = userInterface.getInput("Введите модель автомобиля: ");
                int year = Integer.parseInt(userInterface.getInput("Введите год выпуска автомобиля: "));
                int price = Integer.parseInt(userInterface.getInput("Введите цену автомобиля: "));
                String condition = userInterface.getInput("Введите состояние автомобиля: ");
                Car car = new Car(mark, model, year, price, condition);
                carCollection.addCar(car);
            } catch (NumberFormatException e) {
                userInterface.showMessage("Цена и год выпуска должны быть числом");
            }
        } else {
            System.out.println("Данное действие может выполнять только админ и менеджер");
        }
    }

    @Override
    public String getName() {
        return "add_car";
    }

}
