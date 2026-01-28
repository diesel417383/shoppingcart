package com.chemin.backend.controller;

import com.chemin.backend.dto.request.CreateAddressRequest;
import com.chemin.backend.dto.response.AddressResponse;
import com.chemin.backend.entity.Address;
import com.chemin.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{userId}")
    public ResponseEntity<AddressResponse> getAddressByUserId(@PathVariable Long userId) {
        return addressService.findByUserId(userId)
                .map(address -> ResponseEntity.ok(
                        addressService.mapToAddressResponse(address)
                ))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody CreateAddressRequest createAddressRequest) {
        Address createdAddress = addressService.createAddress(createAddressRequest);
        AddressResponse addressResponse = addressService.mapToAddressResponse(createdAddress);
        return ResponseEntity.ok(addressResponse);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long addressId, @RequestBody CreateAddressRequest createAddressRequest) {
        Address address = addressService.updateAddress(addressId, createAddressRequest);
        AddressResponse addressResponse = addressService.mapToAddressResponse(address);
        return ResponseEntity.ok(addressResponse);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteById(addressId);
        return ResponseEntity.noContent().build();
    }

}
