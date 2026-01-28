package com.chemin.backend.service;

import com.chemin.backend.Mapper.AddressMapper;
import com.chemin.backend.dto.request.CreateAddressRequest;
import com.chemin.backend.dto.response.AddressResponse;
import com.chemin.backend.entity.Address;
import com.chemin.backend.entity.User;
import com.chemin.backend.exception.ResourceNotFoundException;
import com.chemin.backend.repository.AddressRepository;
import com.chemin.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<Address> findByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Transactional
    public Address createAddress(CreateAddressRequest createAddressRequest){
        User user = userRepository.findById(createAddressRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return addressRepository.save(addressMapper.fromCreateAddress(user, createAddressRequest));
    }

    @Transactional
    public Address updateAddress(Long addressId, CreateAddressRequest createAddressRequest) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address ID not found"));

        return addressRepository.save(addressMapper.fromUpdateAddress(address, createAddressRequest));
    }

    @Transactional
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }

    public AddressResponse mapToAddressResponse(Address address){
        return addressMapper.toResponse(address);
    }
}
