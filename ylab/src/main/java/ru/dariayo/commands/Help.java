package ru.dariayo.commands;

import ru.dariayo.Command;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class Help extends Command {
    private final ConsoleUserInterface userInterface;

    public Help(ConsoleUserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        userInterface.showMessage("add_car - добавить авто");
        userInterface.showMessage("buy - оформить заказ на покупку авто");
        userInterface.showMessage("cancel - отменить заказ");
        userInterface.showMessage("change_order - поменять статус заказа(только для админа и менеджера)");
        userInterface.showMessage("filter_users - вывести отсортированный список пользователей по параметру");
        userInterface.showMessage("login - авторизироваться");
        userInterface.showMessage("register - зарегистрироваться");
        userInterface.showMessage("remove_car - удалить авто");
        userInterface.showMessage("search - найти заказ по номеру");
        userInterface.showMessage("search_car - найти авто по параметру");
        userInterface.showMessage("search_order - найти заказ по параметру");
        userInterface.showMessage("show - список всех автомобилей");
        userInterface.showMessage("show_logs - вывести логи по действиям пользователей");
        userInterface.showMessage("show_users - список всех зарегистрированных пользователей");
        userInterface.showMessage("update_car - обновить данные об авто");
    }

    @Override
    public String getName() {
        return "help";
    }

}
