package ru.dariayo.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.validation.OrderValidator;

@WebServlet("/api/cancelOrder")
public class CancelOrderServlet extends HttpServlet {

    private final OrderCollection orderCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CancelOrderServlet(OrderCollection orderCollection) {
        this.orderCollection = orderCollection;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("orderId");

            if (!OrderValidator.isValidOrderId(idParam)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), "Invalid or missing order ID.");
                return;
            }

            int id = Integer.parseInt(idParam);

            orderCollection.changeStatus(id, "cancel");

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), "Order cancelled successfully.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), "An error occurred: " + e.getMessage());
        }
    }
}
