package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dariayo.dto.OrderDTO;
import ru.dariayo.mapper.OrderMapper;
import ru.dariayo.model.Order;
import ru.dariayo.repositories.OrderCollection;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderCollection orderRepository;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @Autowired
    public OrderController(OrderCollection orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<?> makeOrder(@RequestParam String buyerName, @RequestParam String carMark) {
        if (buyerName == null || carMark == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is missing");
        }
        Order order = new Order(buyerName, carMark, "PENDING");
        orderRepository.makeOrder(buyerName, carMark);
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable int id, @RequestParam String status) {
        if (status == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is missing");
        }
        orderRepository.changeStatus(id, status);
        Order updatedOrder = orderRepository.getOrder(id);
        OrderDTO updatedOrderDTO = orderMapper.orderToOrderDTO(updatedOrder);
        return ResponseEntity.ok(updatedOrderDTO);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable int id) {
        Order order = orderRepository.getOrder(id);
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchOrder(@RequestParam String param, @RequestParam String value) {
        if (param == null || value == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is missing");
        }
        List<OrderDTO> orders = orderRepository.searchOrder(param, value).stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }
}
