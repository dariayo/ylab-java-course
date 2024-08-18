package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/help")
public class HelpServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Создание списка команд и их описаний
        Map<String, String> commands = new HashMap<>();
        commands.put("add_car", "добавить авто");
        commands.put("buy", "оформить заказ на покупку авто");
        commands.put("cancel", "отменить заказ");
        commands.put("change_order", "поменять статус заказа (только для админа и менеджера)");
        commands.put("filter_users", "вывести отсортированный список пользователей по параметру");
        commands.put("login", "авторизироваться");
        commands.put("register", "зарегистрироваться");
        commands.put("remove_car", "удалить авто");
        commands.put("search", "найти заказ по номеру");
        commands.put("search_car", "найти авто по параметру");
        commands.put("search_order", "найти заказ по параметру");
        commands.put("show", "список всех автомобилей");
        commands.put("show_logs", "вывести логи по действиям пользователей");
        commands.put("show_users", "список всех зарегистрированных пользователей");
        commands.put("update_car", "обновить данные об авто");

        // Установка статуса успешного выполнения
        resp.setStatus(HttpServletResponse.SC_OK);
        // Отправка JSON-ответа с командами
        objectMapper.writeValue(resp.getWriter(), commands);
    }
}