package com.chemin.backend.controller;

import com.chemin.backend.model.dto.CreateOrderRequest;
import com.chemin.backend.model.vo.OrderItemResponse;
import com.chemin.backend.model.vo.OrderResponse;
import com.chemin.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemResponse>> getOrderItems(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrderItemsByOrderId(orderId));
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrderFromCart(request.getUserId(), request.getAddressId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
//        return orderService.findById(id)
//                .map(order -> {
//                    order.setStatus(orderDetails.getStatus());
//                    order.setUpdatedAt(java.time.LocalDateTime.now());
//                    return ResponseEntity.ok(orderService.save(order));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
}
