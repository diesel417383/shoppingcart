package com.chemin.backend.unit.controller;

import com.chemin.backend.controller.ProductController;
import com.chemin.backend.entity.Product;
import com.chemin.backend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() throws Exception {

        // 準備測試數據
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("測試商品1");
        product1.setDescription("測試描述1");
        product1.setPrice(BigDecimal.valueOf(100.00));
        product1.setStock(10);
        product1.setCategory("測試分類");
        product1.setCreatedAt(LocalDateTime.now());

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("測試商品2");
        product2.setDescription("測試描述2");
        product2.setPrice(BigDecimal.valueOf(200.00));
        product2.setStock(20);
        product2.setCategory("測試分類");
        product2.setCreatedAt(LocalDateTime.now());

        when(productService.findAll()).thenReturn(Arrays.asList(product1, product2));

        // 執行測試
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("測試商品1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("測試商品2"));
    }

//    @Test
//    void createProduct_shouldReturnProduct() throws Exception {
//        // 準備測試數據
//        Product productToCreate = new Product();
//        productToCreate.setName("新商品");
//        productToCreate.setDescription("新商品描述");
//        productToCreate.setPrice(BigDecimal.valueOf(150.00));
//        productToCreate.setStock(5);
//        productToCreate.setCategory("新分類");
//
//        Product createdProduct = new Product();
//        createdProduct.setId(1L);
//        createdProduct.setName("新商品");
//        createdProduct.setDescription("新商品描述");
//        createdProduct.setPrice(BigDecimal.valueOf(150.00));
//        createdProduct.setStock(5);
//        createdProduct.setCategory("新分類");
//        createdProduct.setCreatedAt(LocalDateTime.now());
//
//        when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);
//
//        // 執行測試
//        mockMvc.perform(post("/api/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(productToCreate)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("新商品"))
//                .andExpect(jsonPath("$.description").value("新商品描述"))
//                .andExpect(jsonPath("$.price").value(150.00))
//                .andExpect(jsonPath("$.stock").value(5))
//                .andExpect(jsonPath("$.category").value("新分類"));
//    }

    @Test
    void getProductById_ShouldReturnProduct_WhenExists() throws Exception {
        // 準備測試數據
        Product product = new Product();
        product.setId(1L);
        product.setName("測試商品");
        product.setDescription("測試描述");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setStock(10);
        product.setCategory("測試分類");
        product.setCreatedAt(LocalDateTime.now());

        when(productService.getProductById(1L)).thenReturn(product);

        // 執行測試
        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("測試商品"))
                .andExpect(jsonPath("$.description").value("測試描述"));
    }

}
