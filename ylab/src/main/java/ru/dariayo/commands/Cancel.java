package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.OrderCollection;

public class Cancel extends Command {
    private final OrderCollection orderCollection;

    public Cancel(OrderCollection orderCollection) {
        this.orderCollection = orderCollection;
    }

    @Override
    public void execute() {
        System.out.println("Введите номер заказа, который хотите отменить: ");
        try (Scanner scanner = new Scanner(System.in)) {
            int id = Integer.parseInt(scanner.nextLine());
            orderCollection.changeStatus(id, "cancel");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        System.out.println("Заказ отменен");
    }

    @Override
    public String getName() {
        return "cancel";
    }

}
