package com.chemin.backend.unit.service;

import com.chemin.backend.entity.Product;
import com.chemin.backend.repository.ProductRepository;
import com.chemin.backend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void findAll_shouldReturnAllProducts() {
        // Given
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Address 1");
        product1.setPrice(BigDecimal.valueOf(100.0));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Address 2");
        product2.setPrice(BigDecimal.valueOf(200.0));

        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.findAll();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Address 1");
        assertThat(result.get(1).getName()).isEqualTo("Address 2");
    }

//    @Test
//    void createProduct_shouldReturnProduct() {
//        // Given
//        Product product = new Product();
//        product.setName("New Address");
//        product.setPrice(BigDecimal.valueOf(150.0));
//
//        Product savedProduct = new Product();
//        savedProduct.setId(1L);
//        savedProduct.setName("New Address");
//        savedProduct.setPrice(BigDecimal.valueOf(150.0));
//        savedProduct.setCreatedAt(LocalDateTime.now());
//
//        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
//
//        // When
//        Product result = productService.createProduct(product);
//
//        // Then
//        assertThat(result.getId()).isEqualTo(1L);
//        assertThat(result.getName()).isEqualTo("New Address");
//        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(150.0));
//        assertThat(result.getCreatedAt()).isNotNull();
//    }

//    @Test
//    void getProductById_shouldReturnProduct_whenProductFound() {
//        // Given
//        Long productId = 1L;
//        Address product = new Address();
//        product.setId(productId);
//        product.setName("Test Address");
//        product.setPrice(BigDecimal.valueOf(100.0));
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        // When
//        Optional<Address> result = productService.getProductById(productId);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get().getId()).isEqualTo(productId);
//        assertThat(result.get().getName()).isEqualTo("Test Address");
//    }

    @Test
    void getProductById_shouldReturnEmpty_whenProductNotFound() {
        // Given
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        Product result = productService.getProductById(productId);

        // Then
        assertThat(result).isEqualTo(999L);
    }
}
