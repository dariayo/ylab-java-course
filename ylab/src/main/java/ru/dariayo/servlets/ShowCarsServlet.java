package ru.dariayo.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dariayo.repositories.CarCollection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/showCars")
public class ShowCarsServlet extends HttpServlet {

    private final CarCollection carCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ShowCarsServlet() {
        // Конструктор без параметров для соответствия спецификации сервлетов
        this.carCollection = new CarCollection();
    }

    public ShowCarsServlet(CarCollection carCollection) {
        this.carCollection = carCollection;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<?> cars = carCollection.getCars(); // Получаем список машин
            
            if (cars.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                objectMapper.writeValue(resp.getWriter(), new ApiResponse("No cars available."));
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), cars);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ApiResponse("An error occurred: " + e.getMessage()));
        }
    }

    // Класс для форматирования JSON-ответов
    private static class ApiResponse {
        private final String message;

        public ApiResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}