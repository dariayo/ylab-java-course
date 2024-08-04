package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.OrderCollection;

public class ChangeOrder extends Command {
    private final OrderCollection orderCollection;

    public ChangeOrder(OrderCollection orderCollection) {
        this.orderCollection = orderCollection;
    }
    //TODO
    @Override
    public void execute() {
        System.out.println(
                "Введите номер заказа, у которого хотите поменять статус.");
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите текущий статус заказа. Выберите из списка: оформлен, закрыт, оплачен");
        String status = scanner.nextLine();
        orderCollection.changeStatus(id, status);
        System.out.println("Статус заказа изменен");
    }

    @Override
    public String getName() {
        return "change_status";
    }
}
