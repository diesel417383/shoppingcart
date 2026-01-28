package com.chemin.backend.Mapper;

import com.chemin.backend.dto.request.CreateAddressRequest;
import com.chemin.backend.dto.response.AddressResponse;
import com.chemin.backend.entity.Address;
import com.chemin.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressResponse toResponse(Address address);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Address fromCreateAddress(User user, CreateAddressRequest createAddressRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Address fromUpdateAddress(@MappingTarget Address address, CreateAddressRequest createAddressRequest);

}
