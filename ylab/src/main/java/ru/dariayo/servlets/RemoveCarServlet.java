package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.PersonCollection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/remove_car")
public class RemoveCarServlet extends HttpServlet {

    private final CarCollection carCollection;
    private final PersonCollection personCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RemoveCarServlet(CarCollection carCollection, PersonCollection personCollection) {
        this.carCollection = carCollection;
        this.personCollection = personCollection;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String role = personCollection.getPerson().getRole();
        if (!"admin".equals(role) && !"manager".equals(role)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Map<String, String> responseData = new HashMap<>();
            responseData.put("message", "Доступ запрещен. Только администратор или менеджер могут удалять автомобили.");
            objectMapper.writeValue(resp.getWriter(), responseData);
            return;
        }

        Map<String, String> requestData = objectMapper.readValue(req.getInputStream(), Map.class);
        String mark = requestData.get("mark");
        String model = requestData.get("model");

        if (mark == null || model == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> responseData = new HashMap<>();
            responseData.put("message", "Марка и модель автомобиля обязательны.");
            objectMapper.writeValue(resp.getWriter(), responseData);
            return;
        }

        boolean carRemoved = carCollection.removeCar(mark, model);

        Map<String, String> responseData = new HashMap<>();
        if (carRemoved) {
            resp.setStatus(HttpServletResponse.SC_OK);
            responseData.put("message", "Автомобиль успешно удален.");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseData.put("message", "Автомобиль не найден.");
        }

        objectMapper.writeValue(resp.getWriter(), responseData);
    }
}