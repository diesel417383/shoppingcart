package com.chemin.backend.mapper;

import com.chemin.backend.model.dto.CreateAddressRequest;
import com.chemin.backend.model.vo.AddressResponse;
import com.chemin.backend.model.entity.Address;
import com.chemin.backend.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressResponse toResponse(Address address);

    List<AddressResponse> toResponseList(List<Address> addressList);

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
