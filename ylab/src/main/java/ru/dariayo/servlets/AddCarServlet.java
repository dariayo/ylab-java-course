package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dariayo.dto.CarDTO;
import ru.dariayo.mapper.CarMapper;
import ru.dariayo.model.Car;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.PersonCollection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/addCar")
public class AddCarServlet extends HttpServlet {

    private final CarCollection carCollection;
    private final PersonCollection personCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AddCarServlet(CarCollection carCollection, PersonCollection personCollection) {
        this.carCollection = carCollection;
        this.personCollection = personCollection;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CarDTO carDTO = objectMapper.readValue(req.getReader(), CarDTO.class);
            String role = personCollection.getPerson().getRole();
            if ("admin".equals(role) || "manager".equals(role)) {
                Car car = CarMapper.INSTANCE.carDTOToCar(carDTO);

                carCollection.addCar(car);

                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectMapper.writeValue(resp.getWriter(), CarMapper.INSTANCE.carToCarDTO(car));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                objectMapper.writeValue(resp.getWriter(), "This action is only allowed for admin and manager.");
            }
        } catch (SQLException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), e.getMessage());
        }
    }
}