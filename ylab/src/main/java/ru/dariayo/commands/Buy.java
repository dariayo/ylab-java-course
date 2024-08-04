package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.repositories.PersonCollection;

public class Buy extends Command {
    private final PersonCollection personCollection;
    private final OrderCollection orderCollection;
    private final CarCollection carCollection;

    public Buy(PersonCollection personCollection, CarCollection carCollection, OrderCollection orderCollection) {
        this.personCollection = personCollection;
        this.carCollection = carCollection;
        this.orderCollection = orderCollection;
    }

    @Override
    public void execute() {
        System.out.println("Выберите марку и модель автомобиля из списка: ");
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
