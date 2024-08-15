package ru.dariayo.commands;

import ru.dariayo.Command;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class SearchOrder extends Command {
    private final OrderCollection orderCollection;
    private final ConsoleUserInterface userInterface;

    public SearchOrder(OrderCollection orderCollection, ConsoleUserInterface userInterface) {
        this.orderCollection = orderCollection;
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        String param = userInterface.getInput("Введите параметр для поиска: client, status, car");
        orderCollection.searchOrder(param);

    }

    @Override
    public String getName() {
        return "search_car";
    }

}
