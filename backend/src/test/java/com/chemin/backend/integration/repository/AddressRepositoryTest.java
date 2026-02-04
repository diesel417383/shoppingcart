package com.chemin.backend.integration.repository;

import com.chemin.backend.model.entity.Address;
import com.chemin.backend.model.entity.User;
import com.chemin.backend.repository.AddressRepository;
import com.chemin.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserIdAndIsDefaultTrue_shouldReturnDefaultAddress() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("123456");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        Address addr1 = new Address();
        addr1.setUser(user);
        addr1.setRecipientName("John Doe");
        addr1.setIsDefault(true);
        addr1.setCreatedAt(LocalDateTime.now());
        addressRepository.save(addr1);

        Address addr2 = new Address();
        addr2.setUser(user);
        addr2.setRecipientName("Jane Doe");
        addr2.setIsDefault(false);
        addr2.setCreatedAt(LocalDateTime.now());
        addressRepository.save(addr2);

        // When
        Optional<Address> result = addressRepository.findByUserIdAndIsDefaultTrue(user.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getIsDefault()).isTrue();
        assertThat(result.get().getRecipientName()).isEqualTo("John Doe");
    }

    @Test
    void findByUserIdAndIsDefaultTrue_shouldReturnEmpty(){

        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("123456");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        Address addr1 = new Address();
        addr1.setUser(user);
        addr1.setRecipientName("John Doe");
        addr1.setIsDefault(false);
        addr1.setCreatedAt(LocalDateTime.now());
        addressRepository.save(addr1);

        Address addr2 = new Address();
        addr2.setUser(user);
        addr2.setRecipientName("Jane Doe");
        addr2.setIsDefault(false);
        addr2.setCreatedAt(LocalDateTime.now());
        addressRepository.save(addr2);

        // When
        Optional<Address> result = addressRepository.findByUserIdAndIsDefaultTrue(user.getId());

        assertThat(result).isEmpty();
    }

}
