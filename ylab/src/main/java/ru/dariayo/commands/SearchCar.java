package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.CarCollection;

public class SearchCar extends Command {
    private final CarCollection carCollection;

    public SearchCar(CarCollection carCollection) {
        this.carCollection = carCollection;
    }

    @Override
    public void execute() {
        System.out.println("Введите параметр для поиска: mark, model, price, year");
        Scanner scanner = new Scanner(System.in);
        String param = scanner.nextLine();
        carCollection.searchCar(param);

    }

    @Override
    public String getName() {
        return "search_car";
    }

}
