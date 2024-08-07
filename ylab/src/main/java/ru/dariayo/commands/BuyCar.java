package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class BuyCar extends Command {
    private final PersonCollection personCollection;
    private final OrderCollection orderCollection;
    private final CarCollection carCollection;
    private final ConsoleUserInterface userInterface;

    public BuyCar(PersonCollection personCollection, CarCollection carCollection, OrderCollection orderCollection,
            ConsoleUserInterface userInterface) {
        this.personCollection = personCollection;
        this.carCollection = carCollection;
        this.orderCollection = orderCollection;
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        userInterface.showMessage("Выберите марку и модель автомобиля из списка: ");
        carCollection.showCars();
        System.out.println("Введите марку и модель автомобиля: ");
        try (Scanner scanner = new Scanner(System.in)) {
            String mark = scanner.nextLine();
            String model = scanner.nextLine();
            orderCollection.makeOrder(personCollection.getPerson(), mark, model, carCollection);
        }

    }

    @Override
    public String getName() {
        return "buy";
    }

}
