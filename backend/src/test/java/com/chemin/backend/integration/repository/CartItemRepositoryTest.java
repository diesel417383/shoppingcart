package com.chemin.backend.integration.repository;

import com.chemin.backend.entity.CartItem;
import com.chemin.backend.entity.Product;
import com.chemin.backend.entity.User;
import com.chemin.backend.repository.CartItemRepository;
import com.chemin.backend.repository.ProductRepository;
import com.chemin.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@DataJpaTest
public class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    @Test
    void findByUserAndProductId_shouldReturnCartItems(){

        User user = new User();
        user.setUsername("Jane");
        user.setEmail("test@gmail.com");
        user.setPassword("test");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Product product = new Product();
        product.setName("Test Address");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("99.99"));
        product.setStock(10);
        product.setCategory("Test Category");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cartItem.setCreatedAt(LocalDateTime.now());

        cartItemRepository.save(cartItem);

        Optional<CartItem> foundCartItem = cartItemRepository.findByUserAndProductId(user, product.getId());

        assertThat(foundCartItem).isPresent();
        assertThat(foundCartItem.get().getUser()).isEqualTo(user);
        assertThat(foundCartItem.get().getProduct()).isEqualTo(product);
        assertThat(foundCartItem.get().getQuantity()).isEqualTo(2);

    }

    @Test
    void findByUserAndProductId_shouldReturnCartEmpty(){

        User user = new User();
        user.setUsername("John");
        user.setEmail("john@test.com");
        user.setPassword("test");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Product product = new Product();
        product.setName("Another Address");
        product.setDescription("Another Description");
        product.setPrice(new BigDecimal("49.99"));
        product.setStock(5);
        product.setCategory("Another Category");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);

        Optional<CartItem> foundCartItem = cartItemRepository.findByUserAndProductId(user, product.getId());

        assertThat(foundCartItem).isEmpty();

    }
}
