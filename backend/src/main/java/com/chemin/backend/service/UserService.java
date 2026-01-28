package com.chemin.backend.service;

import com.chemin.backend.Mapper.UserMapper;
import com.chemin.backend.dto.request.UserRegisterRequest;
import com.chemin.backend.dto.response.UserRegisterResponse;
import com.chemin.backend.entity.User;
import com.chemin.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("UserId not found"));
    }

    public User createUser(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());

        return userRepository.save(user);
    }

    public UserRegisterResponse mapToUserRegisterResponse(User user){
        return userMapper.toRegisterResponse(user);
    }

}
