package com.chemin.backend.Mapper;

import com.chemin.backend.dto.response.UserRegisterResponse;
import com.chemin.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterResponse toRegisterResponse(User user);
}
