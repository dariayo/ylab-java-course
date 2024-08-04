package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.OrderCollection;

public class Search extends Command {
    private final OrderCollection orderCollection;

    public Search(OrderCollection orderCollection) {
        this.orderCollection = orderCollection;
    }

    // TODO
    @Override
    public void execute() {
        System.out.println(
                "Введите номер заказа");
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        orderCollection.getOrder(id);
    }

    @Override
    public String getName() {
        return "search";
    }
}