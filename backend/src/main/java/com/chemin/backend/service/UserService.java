package com.chemin.backend.service;

import com.chemin.backend.annotation.LogExecution;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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

    @LogExecution
    public UserLoginResponse userLogin(UserLoginRequest userLoginRequest) {
        Map<String, Object> claims = new HashMap<>();

        String account = userLoginRequest.getAccount();
        String password = userLoginRequest.getPassword();

        log.info("使用者登入 :{} ", account);

        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> {
                    log.warn("登入失敗: 帳號錯誤, account={}", account);
                    return new ResourceNotFoundException("帳戶不存在");
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("登入失敗：密碼錯誤, account={}, userId={}", account, user.getId());
            throw new RuntimeException("密碼錯誤");
        }

        claims.put("userId", user.getId());
        claims.put("account", user.getAccount());
        claims.put("role", user.getRole());
        String token = jwtUtil.generateToke(claims, user.getAccount());

        log.info("登入成功, account={}", account);

        UserLoginResponse userLoginResponse = userMapper.toLoginResponse(user);

        userLoginResponse.setToken(token);
        return userLoginResponse;
    }

    @LogExecution
    @Transactional
    public UserRegisterResponse userRegister(UserRegisterRequest userRegisterRequest) {
        if (userRepository.existsByAccount(userRegisterRequest.getAccount())) {
            log.warn("使用者帳號重複, account={}", userRegisterRequest.getAccount());
            throw new RuntimeException("帳戶重複");
        }
        String encryptPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        User user = new User();
        user.setAccount(userRegisterRequest.getAccount());
        user.setPassword(encryptPassword);
        user.setEmail(userRegisterRequest.getEmail());
        userRepository.save(user);

        log.info("使用者註冊成功: account={}, userId={}", user.getAccount(), user.getId());

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
