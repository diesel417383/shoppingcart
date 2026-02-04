package com.chemin.backend.controller;

import com.chemin.backend.model.dto.CreateAddressRequest;
import com.chemin.backend.model.vo.AddressResponse;
import com.chemin.backend.model.entity.Address;
import com.chemin.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<AddressResponse>> getAddressByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody CreateAddressRequest createAddressRequest) {
        return ResponseEntity.ok(addressService.createAddress(createAddressRequest));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long addressId, @RequestBody CreateAddressRequest createAddressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, createAddressRequest));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteById(addressId);
        return ResponseEntity.noContent().build();
    }

}
