package ru.dariayo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.SQLException;
import ru.dariayo.model.Person;
import ru.dariayo.model.PersonDTO;
import ru.dariayo.model.PersonMapper;
import ru.dariayo.repositories.PersonCollection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/api/register")
public class RegisterServlet extends HttpServlet {
    private PersonCollection personCollection;
    private ObjectMapper objectMapper;

    @Override
    public void init() {
        this.personCollection = new PersonCollection(); // Нужно настроить DI или создать экземпляр вручную
        this.objectMapper = new ObjectMapper(); // Jackson ObjectMapper для работы с JSON
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Чтение и десериализация JSON запроса в DTO
            PersonDTO personDTO = objectMapper.readValue(req.getReader(), PersonDTO.class);

            // Валидация (можно использовать Hibernate Validator или вручную)
            if (!validatePersonDTO(personDTO)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid data");
                return;
            }

            // Преобразование DTO в сущность
            Person person = PersonMapper.INSTANCE.toEntity(personDTO);

            // Добавление нового пользователя
            personCollection.addPerson(person);

            // Возвращаем успешный ответ
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(personDTO));

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    private boolean validatePersonDTO(PersonDTO personDTO) {
        // Простая валидация, можно использовать Bean Validation API
        return personDTO.getName() != null && !personDTO.getName().isEmpty()
                && personDTO.getPassword() != null && !personDTO.getPassword().isEmpty()
                && personDTO.getContacts() != null && !personDTO.getContacts().isEmpty();
    }

    // Scanner scanner = new Scanner(System.in);
    // String pass = "123";

    // @Override
    // public void execute() throws SQLException {
    // String username = getUsername();
    // String password = getPassword();
    // String role = getRole();
    // String contacts = getContacts();
    // Person person = new Person(username, password, role, contacts);
    // personCollection.addPerson(person);

    // }

    // public String getUsername() {
    // return userInterface.getInput("Введите имя пользователя:");
    // }

    // public String getPassword() {
    // return userInterface.getInput("Введите пароль:");
    // }

    // public String getRole() {
    // String role = userInterface.getInput("Выберите роль: admin, manager, user");
    // if (role.equals("admin") || role.equals("manager")) {
    // if (userInterface.getInput("Введите пароль:") == pass) {
    // return role;
    // }
    // }
    // return role;
    // }

    // public String getContacts() {
    // return userInterface.getInput("Введите почту:");
    // }

    // @Override
    // public String getName() {
    // return "register";
    // }

}
