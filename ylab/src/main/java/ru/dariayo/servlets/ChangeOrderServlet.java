package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dariayo.repositories.OrderCollection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/changeOrderStatus")
public class ChangeOrderServlet extends HttpServlet {

    private final OrderCollection orderCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChangeOrderServlet(OrderCollection orderCollection) {
        this.orderCollection = orderCollection;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String idParam = req.getParameter("orderId");
            String status = req.getParameter("status");

            if (idParam == null || idParam.isEmpty() || status == null || status.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), "Order ID and status are required.");
                return;
            }

            int id = Integer.parseInt(idParam);

            orderCollection.changeStatus(id, status);

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), "Order status changed successfully.");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Invalid order ID.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), "An error occurred: " + e.getMessage());
        }
    }
}