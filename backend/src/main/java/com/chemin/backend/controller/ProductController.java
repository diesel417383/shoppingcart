package com.chemin.backend.controller;

import com.chemin.backend.dto.request.CreateProductRequest;
import com.chemin.backend.dto.response.ProductResponse;
import com.chemin.backend.entity.Product;
import com.chemin.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        ProductResponse productResponse = productService.mapToProductResponse(product);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> productResponseList = productService.mapToProductListResponse(productService.findAll());
        return ResponseEntity.ok(productResponseList);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        Product product = productService.createProduct(createProductRequest);
        return ResponseEntity.ok(productService.mapToProductResponse(product));
    }

}
