package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.model.Person;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/updateCar")
public class UpdateCarServlet extends HttpServlet {
    private final CarCollection carCollection;
    private final PersonCollection personCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UpdateCarServlet() {
        // Конструктор без параметров для соответствия спецификации сервлетов
        this.carCollection = new CarCollection(); // Замените это на реальную инициализацию
        this.personCollection = new PersonCollection(); // Замените это на реальную инициализацию
    }

    public UpdateCarServlet(CarCollection carCollection, PersonCollection personCollection) {
        this.carCollection = carCollection;
        this.personCollection = personCollection;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Получаем данные из запроса
            String mark = req.getParameter("mark");
            String model = req.getParameter("model");
            Person currentPerson = personCollection.getPerson();

            // Проверяем права пользователя
            if (currentPerson == null
                    || !(currentPerson.getRole().equals("admin") || currentPerson.getRole().equals("manager"))) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                objectMapper.writeValue(resp.getWriter(),
                        new ApiResponse("You do not have permission to update the car."));
                return;
            }

            // Проверяем корректность входных данных
            if (mark == null || mark.isEmpty() || model == null || model.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), new ApiResponse("Mark and model are required."));
                return;
            }

            // Выполняем обновление
            carCollection.updateCar(mark, model);

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), new ApiResponse("Car updated successfully."));
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
