package ru.dariayo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CarCollection {

    private final JdbcTemplate jdbcTemplate;
    private final AuditLogRepository auditLogRepository;

    @Autowired
    public CarCollection(JdbcTemplate jdbcTemplate, AuditLogRepository auditLogRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditLogRepository = auditLogRepository;
    }

    public List<Car> getCars() {
        String query = "SELECT * FROM cs_schema.cars";
        return jdbcTemplate.query(query, this::mapRowToCar);
    }

    public void addCar(Car car) {
        String sql = "INSERT INTO cs_schema.cars (mark, model, year, price, condition) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, car.getMark(), car.getModel(), car.getYearOfIssue(), car.getPrice(),
                car.getCondition());
        auditLogRepository.logAction("System", "Add car", "Mark: " + car.getMark() + " Model: " + car.getModel());
    }

    public boolean removeCar(String mark, String model) {
        String sql = "DELETE FROM cs_schema.cars WHERE mark = ? AND model = ?";
        int rowsAffected = jdbcTemplate.update(sql, mark, model);
        if (rowsAffected > 0) {
            auditLogRepository.logAction("System", "Remove car", "Mark: " + mark + " Model: " + model);
            return true;
        }
        return false;
    }

    public void updateCar(String mark, String model, Car updatedCar) {
        String sql = "UPDATE cs_schema.cars SET mark = ?, model = ?, year = ?, price = ?, condition = ? WHERE mark = ? AND model = ?";
        jdbcTemplate.update(sql, updatedCar.getMark(), updatedCar.getModel(), updatedCar.getYearOfIssue(),
                updatedCar.getPrice(), updatedCar.getCondition(), mark, model);
        auditLogRepository.logAction("System", "Update car", "Mark: " + mark + " Model: " + model);
    }

    public Car getByMark(String mark, String model) {
        String sql = "SELECT * FROM cs_schema.cars WHERE mark = ? AND model = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToCar, mark, model);
    }

    public List<Car> searchCar(String param, String value) {
        String sql = String.format("SELECT * FROM cs_schema.cars WHERE %s = ?", param);
        return jdbcTemplate.query(sql, this::mapRowToCar, value);
    }

    private Car mapRowToCar(ResultSet rs, int rowNum) throws SQLException {
        return new Car(
                rs.getString("mark"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getInt("price"),
                rs.getString("condition"));
    }
}
