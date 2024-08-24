package ru.dariayo.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dariayo.repositories.CarCollection;

public class SearchCarServlet extends HttpServlet {

    private final CarCollection carCollection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SearchCarServlet(CarCollection carCollection) {
        this.carCollection = carCollection;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String param = req.getParameter("param");

        if (param == null || param.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Param are required.");
            return;
        }

        carCollection.searchCar(param);

        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter(), "Car find.");

    }
}
