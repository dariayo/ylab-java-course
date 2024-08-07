package ru.dariayo.commands;

import ru.dariayo.Command;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class ChangeOrder extends Command {
    private final OrderCollection orderCollection;
    private final ConsoleUserInterface userInterface;

    public ChangeOrder(OrderCollection orderCollection, ConsoleUserInterface userInterface) {
        this.orderCollection = orderCollection;
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        int id = Integer.parseInt(userInterface.getInput("Введите номер заказа, у которого хотите поменять статус."));
        String status = userInterface
                .getInput("Введите текущий статус заказа. Выберите из списка: оформлен, закрыт, оплачен");
        orderCollection.changeStatus(id, status);
        userInterface.showMessage("Статус заказа изменен");
    }

    @Override
    public String getName() {
        return "change_status";
    }
}
