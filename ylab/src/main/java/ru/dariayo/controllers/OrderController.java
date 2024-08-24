package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dariayo.model.Order;
import ru.dariayo.repositories.OrderCollection;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderCollection orderRepository;

    @Autowired
    public OrderController(OrderCollection orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<String> makeOrder(@RequestParam String buyerName, @RequestParam String carMark) {
        orderRepository.makeOrder(buyerName, carMark);
        return ResponseEntity.ok("Order placed successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable int id, @RequestParam String status) {
        orderRepository.changeStatus(id, status);
        return ResponseEntity.ok("Order status updated successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable int id) {
        Order order = orderRepository.getOrder(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Order>> searchOrder(@RequestParam String param, @RequestParam String value) {
        List<Order> orders = orderRepository.searchOrder(param, value);
        return ResponseEntity.ok(orders);
    }
}
