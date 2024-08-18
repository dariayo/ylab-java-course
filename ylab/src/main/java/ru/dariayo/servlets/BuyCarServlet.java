package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.dto.OrderDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/buyCar")
public class BuyCarServlet extends HttpServlet {

    private final PersonCollection personCollection;
    private final OrderCollection orderCollection;
    private final CarCollection carCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BuyCarServlet(PersonCollection personCollection, CarCollection carCollection,
            OrderCollection orderCollection) {
        this.personCollection = personCollection;
        this.carCollection = carCollection;
        this.orderCollection = orderCollection;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            OrderDTO orderRequest = objectMapper.readValue(req.getReader(), OrderDTO.class);

            orderCollection.makeOrder(personCollection.getPerson(), orderRequest.car(), orderRequest.car(),
                    carCollection);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getWriter(), "Order placed successfully.");
        } catch (SQLException e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), "Error placing order: " + e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Invalid request: " + e.getMessage());
        }
    }
}
