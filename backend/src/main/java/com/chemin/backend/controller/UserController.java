package com.chemin.backend.controller;

import com.chemin.backend.model.dto.UserLoginRequest;
import com.chemin.backend.model.vo.OrderResponse;
import com.chemin.backend.model.vo.UserLoginResponse;
import com.chemin.backend.model.vo.UserRegisterResponse;
import com.chemin.backend.model.dto.UserRegisterRequest;
import com.chemin.backend.model.entity.User;
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
    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId){
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.ok(userService.userLogin(userLoginRequest));
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
    public ResponseEntity<UserRegisterResponse> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        return ResponseEntity.ok(userService.userRegister(userRegisterRequest));
    }

}
