package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dariayo.repositories.PersonCollection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    private final PersonCollection personCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginServlet(PersonCollection personCollection) {
        this.personCollection = personCollection;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Чтение входящих данных (JSON)
        Map<String, String> requestData = objectMapper.readValue(req.getInputStream(), Map.class);
        String username = requestData.get("username");
        String password = requestData.get("password");

        Map<String, String> responseData = new HashMap<>();

        if (username == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseData.put("message", "Username and password are required.");
            objectMapper.writeValue(resp.getWriter(), responseData);
            return;
        }

        // Проверка пользователя
        boolean isAuthenticated = personCollection.checkUser(username, password);

        if (isAuthenticated) {
            resp.setStatus(HttpServletResponse.SC_OK);
            responseData.put("message", "Login successful.");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseData.put("message", "Invalid username or password.");
        }

        // Отправка ответа
        objectMapper.writeValue(resp.getWriter(), responseData);
    }
}