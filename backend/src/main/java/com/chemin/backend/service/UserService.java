package com.chemin.backend.service;

import com.chemin.backend.exception.ResourceNotFoundException;
import com.chemin.backend.mapper.UserMapper;
import com.chemin.backend.model.dto.UserLoginRequest;
import com.chemin.backend.model.dto.UserRegisterRequest;
import com.chemin.backend.model.vo.UserLoginResponse;
import com.chemin.backend.model.vo.UserRegisterResponse;
import com.chemin.backend.model.entity.User;
import com.chemin.backend.repository.UserRepository;
import com.chemin.backend.util.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("UserId not found"));
    }

    public UserLoginResponse userLogin(UserLoginRequest userLoginRequest) {

        Map<String, Object> claims = new HashMap<>();

        String account = userLoginRequest.getAccount();
        String password = userLoginRequest.getPassword();

        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> new ResourceNotFoundException("帳戶不存在"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密碼錯誤");
        }

        claims.put("userId", user.getId());
        claims.put("account", user.getAccount());
        claims.put("role", user.getRole());
        String token = jwtUtil.generateToke(claims, user.getAccount());

        UserLoginResponse userLoginResponse = userMapper.toLoginResponse(user);

        userLoginResponse.setToken(token);
        return userLoginResponse;
    }

    public UserRegisterResponse userRegister(UserRegisterRequest userRegisterRequest) {
        if (userRepository.existsByAccount(userRegisterRequest.getAccount())) {
            throw new RuntimeException("帳戶重複");
        }
        String encryptPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        User user = new User();
        user.setAccount(userRegisterRequest.getAccount());
        user.setPassword(encryptPassword);
        user.setEmail(userRegisterRequest.getEmail());
        userRepository.save(user);

        return userMapper.toRegisterResponse(user);
    }

    @PostConstruct
    public void initAdmin() { // 初始化admin
        if (!userRepository.existsByAccount("admin")) {
            User user = new User();
            user.setAccount("admin");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setRole("admin");
            user.setEmail("admin@gmail.com");
            userRepository.save(user);
        }
    }
}
