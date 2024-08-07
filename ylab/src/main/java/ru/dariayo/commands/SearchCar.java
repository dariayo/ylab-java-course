package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class SearchCar extends Command {
    private final CarCollection carCollection;
    private final ConsoleUserInterface userInterface;

    public SearchCar(CarCollection carCollection, ConsoleUserInterface userInterface) {
        this.carCollection = carCollection;
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        String param = userInterface.getInput("Введите параметр для поиска: mark, model, price, year");
        carCollection.searchCar(param);

    }

    @Override
    public String getName() {
        return "search_car";
    }

}
