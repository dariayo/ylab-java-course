package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.model.Person;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
@WebServlet("/api/updateCar")
public class UpdateCarServlet extends HttpServlet {

    private final CarCollection carCollection;
    private final PersonCollection personRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public UpdateCarServlet(CarCollection carCollection, PersonCollection personRepository, ObjectMapper objectMapper) {
        this.carCollection = carCollection;
        this.personRepository = personRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String oldMark = req.getParameter("oldMark");
            String oldModel = req.getParameter("oldModel");
            String newMark = req.getParameter("newMark");
            String newModel = req.getParameter("newModel");
            int year = Integer.parseInt(req.getParameter("year"));
            int price = Integer.parseInt(req.getParameter("price"));
            String condition = req.getParameter("condition");

            Person currentPerson = personCollection.getPerson();

            if (currentPerson == null ||
                    !(currentPerson.getRole().equals("admin") || currentPerson.getRole().equals("manager"))) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                objectMapper.writeValue(resp.getWriter(),
                        new ApiResponse("You do not have permission to update the car."));
                return;
            }

            if (oldMark == null || oldMark.isEmpty() || oldModel == null || oldModel.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), new ApiResponse("Old mark and model are required."));
                return;
            }

            carCollection.updateCar(oldMark, oldModel, newMark, newModel, year, price, condition);

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), new ApiResponse("Car updated successfully."));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ApiResponse("An error occurred: " + e.getMessage()));
        }
    }

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
