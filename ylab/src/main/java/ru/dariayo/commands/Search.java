package ru.dariayo.commands;

import ru.dariayo.Command;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class Search extends Command {
    private final OrderCollection orderCollection;
    private final ConsoleUserInterface userInterface;

    public Search(OrderCollection orderCollection, ConsoleUserInterface userInterface) {
        this.orderCollection = orderCollection;
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        int id = Integer.parseInt(userInterface.getInput("Введите номер заказа"));
        orderCollection.getOrder(id);
    }

    @Override
    public String getName() {
        return "search";
    }
}