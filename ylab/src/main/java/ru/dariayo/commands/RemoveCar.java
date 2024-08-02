package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.CarCollection;
import ru.dariayo.Command;
import ru.dariayo.PersonCollection;

public class RemoveCar extends Command {
    private final CarCollection carCollection;
    private final PersonCollection personCollection;

    public RemoveCar(CarCollection carCollection, PersonCollection personCollection) {
        this.carCollection = carCollection;
        this.personCollection = personCollection;
    }

    @Override
    public void execute() {
        if (personCollection.getPerson().getRole().equals("admin")
                || personCollection.getPerson().getRole().equals("manager")) {
            System.out.println("Введите марку и модель автомобиля, который хотите удалить: ");
            try (Scanner scanner = new Scanner(System.in)) {
                String mark = scanner.nextLine();
                String model = scanner.nextLine();
                carCollection.removeCar(mark, model);
            }

        }

    }

    @Override
    public String getName() {
        return "remove_car";
    }

}
