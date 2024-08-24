package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.model.Person;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/showUsers")
public class ShowUsersServlet extends HttpServlet {
    private final PersonCollection personCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ShowUsersServlet() {

        this.personCollection = new PersonCollection();
    }

    public ShowUsersServlet(PersonCollection personCollection) {
        this.personCollection = personCollection;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<Person> users = personCollection.getUsers();

            if (users.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                objectMapper.writeValue(resp.getWriter(), new ApiResponse("No users found."));
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), users);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ApiResponse("An error occurred: " + e.getMessage()));
        }
    }

    private static class ApiResponse {
        public ApiResponse(String message) {
        }
    }
}
