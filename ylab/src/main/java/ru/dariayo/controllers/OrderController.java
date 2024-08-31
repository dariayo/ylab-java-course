package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dariayo.dto.OrderDTO;
import ru.dariayo.dto.OrderRequestDTO;
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
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        if (orderRequest.getBuyerName() == null || orderRequest.getBuyerName().isBlank() ||
                orderRequest.getCarMark() == null || orderRequest.getCarMark().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Buyer name and car mark are required");
        }
        Order order = new Order(orderRequest.getBuyerName(), orderRequest.getCarMark(), "PENDING");
        orderRepository.makeOrder(orderRequest.getBuyerName(), orderRequest.getCarMark());
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Integer id, @RequestParam String status) {
        if (status == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is missing");
        }
        orderRepository.changeStatus(id, status);
        Order updatedOrder = orderRepository.getOrder(id);
        OrderDTO updatedOrderDTO = orderMapper.orderToOrderDTO(updatedOrder);
        return ResponseEntity.ok(updatedOrderDTO);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Integer id) {
        Order order = orderRepository.getOrder(id);
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findOrderById(@RequestParam String param, @RequestParam String value) {
        if (param == null || value == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is missing");
        }
        List<OrderDTO> orders = orderRepository.searchOrder(param, value).stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }
}
