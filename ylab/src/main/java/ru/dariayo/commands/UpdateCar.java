package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.CarCollection;
import ru.dariayo.Command;
import ru.dariayo.PersonCollection;

public class UpdateCar extends Command {
    private final CarCollection carCollection;
    private final PersonCollection personCollection;

    public UpdateCar(CarCollection carCollection, PersonCollection personCollection) {
        this.carCollection = carCollection;
        this.personCollection = personCollection;
    }

    @Override
    public void execute() {
        if (personCollection.getPerson().getRole().equals("admin")
                || personCollection.getPerson().getRole().equals("manager")) {
            System.out.println("Введите марку и модель автомобиля, параметры которого хотите изменить: ");
            try (Scanner scanner = new Scanner(System.in)) {
                String mark = scanner.nextLine();
                String model = scanner.nextLine();
                carCollection.updateCar(mark, model);
            }
        }

    }

    @Override
    public String getName() {
        return "update_car";
    }

}
