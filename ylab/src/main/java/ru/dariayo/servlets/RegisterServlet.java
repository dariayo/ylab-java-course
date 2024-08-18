package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.PersonCollection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/register")
public class RegisterServlet extends HttpServlet {
    private final PersonCollection personCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RegisterServlet() {

        this.personCollection = new PersonCollection();
    }

    public RegisterServlet(PersonCollection personCollection) {
        this.personCollection = personCollection;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String role = req.getParameter("role");
            String contacts = req.getParameter("contacts");

            if (username == null || username.isEmpty() ||
                    password == null || password.isEmpty() ||
                    role == null || role.isEmpty() ||
                    contacts == null || contacts.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), "All fields are required.");
                return;
            }

            if ((role.equals("admin") || role.equals("manager")) &&
                    !password.equals("123")) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                objectMapper.writeValue(resp.getWriter(), "Invalid password for the given role.");
                return;
            }

            Person person = new Person(username, password, role, contacts);
            personCollection.addPerson(person);

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), "User registered successfully.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), "An error occurred: " + e.getMessage());
        }
    }

    // private static class ApiResponse {
    // public ApiResponse(String message) {
    // }
    // }
}
