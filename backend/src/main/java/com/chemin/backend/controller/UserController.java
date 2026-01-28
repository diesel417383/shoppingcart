package com.chemin.backend.controller;

import com.chemin.backend.Mapper.UserMapper;
import com.chemin.backend.dto.response.OrderResponse;
import com.chemin.backend.dto.response.UserRegisterResponse;
import com.chemin.backend.dto.request.UserRegisterRequest;
import com.chemin.backend.entity.Order;
import com.chemin.backend.entity.User;
import com.chemin.backend.service.OrderService;
import com.chemin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    // Used for testing
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId){
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("{userId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.findByUserId(userId);

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(orders);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest){
        User user = userService.createUser(userRegisterRequest);
        UserRegisterResponse userRegisterResponse = userService.mapToUserRegisterResponse(user);
        return ResponseEntity.ok(userRegisterResponse);
    }

}
