package com.chemin.backend.service;

import com.chemin.backend.mapper.AddressMapper;
import com.chemin.backend.model.dto.CreateAddressRequest;
import com.chemin.backend.model.vo.AddressResponse;
import com.chemin.backend.model.entity.Address;
import com.chemin.backend.model.entity.User;
import com.chemin.backend.exception.ResourceNotFoundException;
import com.chemin.backend.repository.AddressRepository;
import com.chemin.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id not found"));
    }

    public List<AddressResponse> findByUserId(Long userId) {
        List<Address> addressList = addressRepository.findByUserId(userId);
        return addressMapper.toResponseList(addressList);
    }

    @Transactional
    public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {
        User user = userRepository.findById(createAddressRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return addressMapper.toResponse(addressRepository.save(addressMapper.fromCreateAddress(user, createAddressRequest)));
    }

    @Transactional
    public AddressResponse updateAddress(Long addressId, CreateAddressRequest createAddressRequest) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address ID not found"));

        return addressMapper.toResponse(addressRepository.save(addressMapper.fromUpdateAddress(address, createAddressRequest)));
    }

    @Transactional
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
}
