package ru.dariayo.commands;

import ru.dariayo.Command;

public class Help extends Command {

    @Override
    public void execute() {
        System.out.println("add_car - добавить авто");
        System.out.println("buy - оформить заказ на покупку авто");
        System.out.println("cancel - отменить заказ");
        System.out.println("change_order - поменять статус заказа(только для админа и менеджера)");
        System.out.println("filter_users - вывести отсортированный список пользователей по параметру");
        System.out.println("login - авторизироваться");
        System.out.println("register - зарегистрироваться");
        System.out.println("remove_car - удалить авто");
        System.out.println("search - найти заказ по номеру");
        System.out.println("search_car - найти авто по параметру");
        System.out.println("search_order - найти заказ по параметру");
        System.out.println("show - список всех автомобилей");
        System.out.println("show_logs - вывести логи по действиям пользователей");
        System.out.println("show_users - список всех зарегистрированных пользователей");
        System.out.println("update_car - обновить данные об авто");
    }

    @Override
    public String getName() {
        return "help";
    }
    
}
