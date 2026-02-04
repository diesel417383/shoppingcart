package com.chemin.backend.mapper;

import com.chemin.backend.model.vo.UserLoginResponse;
import com.chemin.backend.model.vo.UserRegisterResponse;
import com.chemin.backend.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterResponse toRegisterResponse(User user);

    UserLoginResponse toLoginResponse(User user);
}
