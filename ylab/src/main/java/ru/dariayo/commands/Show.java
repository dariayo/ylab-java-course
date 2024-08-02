package ru.dariayo.commands;

import ru.dariayo.CarCollection;
import ru.dariayo.Command;

public class Show extends Command {
    private final CarCollection carCollection;

    public Show(CarCollection carCollection) {
        this.carCollection = carCollection;
    }

    @Override
    public void execute() {
        carCollection.showCars();
    }

    @Override
    public String getName() {
        return "show";
    }

}
