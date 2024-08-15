package ru.dariayo.commands;

import ru.dariayo.Command;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class Cancel extends Command {
    private final OrderCollection orderCollection;
    private final ConsoleUserInterface userInterface;

    public Cancel(OrderCollection orderCollection, ConsoleUserInterface userInterface) {
        this.orderCollection = orderCollection;
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        try {
            int id = Integer.parseInt(userInterface.getInput("Введите номер заказа, который хотите отменить: "));
            orderCollection.changeStatus(id, "cancel");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        userInterface.showMessage("Заказ отменен");
    }

    @Override
    public String getName() {
        return "cancel";
    }

}
