package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;

public class SearchOrder extends Command {
    private final OrderCollection orderCollection;

    public SearchOrder(OrderCollection orderCollection) {
        this.orderCollection = orderCollection;
    }

    @Override
    public void execute() {
        System.out.println("Введите параметр для поиска: client, status, car");
        Scanner scanner = new Scanner(System.in);
        String param = scanner.nextLine();
        orderCollection.searchOrder(param);

    }

    @Override
    public String getName() {
        return "search_car";
    }

}
