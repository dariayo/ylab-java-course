package ru.dariayo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderCollection {

    private final JdbcTemplate jdbcTemplate;
    private final AuditLogRepository auditLogRepository;

    @Autowired
    public OrderCollection(JdbcTemplate jdbcTemplate, AuditLogRepository auditLogRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditLogRepository = auditLogRepository;
    }

    public void makeOrder(String buyerName, String mark) {
        String sql = "INSERT INTO cs_schema.orders (nameBuyer, status, mark) VALUES (?, 'Placed', ?)";
        jdbcTemplate.update(sql, buyerName, mark);
        auditLogRepository.logAction("System", "Create order", "Buyer: " + buyerName + " Car: " + mark);
    }

    public void changeStatus(int id, String status) {
        String sql = "UPDATE cs_schema.orders SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
        auditLogRepository.logAction("System", "Change order status", "Order ID: " + id + " New Status: " + status);
    }

    public Order getOrder(int id) {
        String sql = "SELECT * FROM cs_schema.orders WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToOrder, id);
    }

    public List<Order> searchOrder(String param, String value) {
        String sql = String.format("SELECT * FROM cs_schema.orders WHERE %s = ?", param);
        return jdbcTemplate.query(sql, this::mapRowToOrder, value);
    }

    private Order mapRowToOrder(ResultSet rs, int rowNum) throws SQLException {
        return new Order(
                rs.getString("nameBuyer"),
                rs.getString("status"),
                rs.getString("mark"));
    }
}
