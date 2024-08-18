package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import ru.dariayo.mapper.CarMapper;
import ru.dariayo.repositories.CarCollection;

@WebServlet("/car")
public class CarServlet extends HttpServlet {
    private ObjectMapper mapper;
    private CarCollection carCollection;
    private final CarMapper carMapper = CarMapper.INSTANCE;

    @Override
    public void init() throws ServletException {
        super.init();
        mapper = new ObjectMapper();
        carCollection = ;
    }
}
