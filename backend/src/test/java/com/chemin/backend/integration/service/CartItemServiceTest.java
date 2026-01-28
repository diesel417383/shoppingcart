package com.chemin.backend.integration.service;

import com.chemin.backend.dto.request.AddProductToCartRequest;
import com.chemin.backend.entity.CartItem;
import com.chemin.backend.entity.Product;
import com.chemin.backend.entity.User;
import com.chemin.backend.repository.CartItemRepository;
import com.chemin.backend.repository.ProductRepository;
import com.chemin.backend.repository.UserRepository;
import com.chemin.backend.service.CartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // 使用 H2 in-memory
@Transactional // 測試完成後回滾
class CartItemServiceTest {

    @Autowired
    private CartItemService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("123456");
        userRepository.save(testUser);

        // 建立測試用 Address
        testProduct = new Product();
        testProduct.setName("Test Address");
        testProduct.setStock(10);
        testProduct.setPrice(BigDecimal.valueOf(100));
        productRepository.save(testProduct);
    }

    @Test
    void addToCart_shouldAddProductToCart_whenValidRequest() {
        // Given
        AddProductToCartRequest dto = new AddProductToCartRequest();
        dto.setUserId(testUser.getId());
        dto.setProductId(testProduct.getId());
        dto.setQuantity(2);

        // When
        cartService.addToCart(dto);

        // Then
        CartItem savedItem = cartItemRepository.findByUserAndProductId(testUser, testProduct.getId())
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        assertEquals(2, savedItem.getQuantity());
        assertEquals(testProduct.getId(), savedItem.getProduct().getId());
        assertEquals(testUser.getId(), savedItem.getUser().getId());
        assertNotNull(savedItem.getCreatedAt());
    }
}
