package ru.dariayo.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dariayo.repositories.OrderCollection;

public class SearchOrderServlet extends HttpServlet {

    private final OrderCollection orderCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SearchOrderServlet(OrderCollection orderCollection) {
        this.orderCollection = orderCollection;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String idParam = req.getParameter("orderId");

            if (idParam == null || idParam.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), "Order ID are required.");
                return;
            }

            int id = Integer.parseInt(idParam);

            orderCollection.getOrder(id);

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), "Order find.");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Invalid order ID.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), "An error occurred: " + e.getMessage());
        }
    }
}
