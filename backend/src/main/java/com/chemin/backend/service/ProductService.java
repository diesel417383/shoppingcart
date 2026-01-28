package com.chemin.backend.service;

import com.chemin.backend.Mapper.ProductMapper;
import com.chemin.backend.dto.request.CreateProductRequest;
import com.chemin.backend.dto.response.ProductResponse;
import com.chemin.backend.entity.Product;
import com.chemin.backend.exception.ResourceNotFoundException;
import com.chemin.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product createProduct(CreateProductRequest createProductRequest){
        Product product = mapToProduct(createProductRequest);
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address ID not found"));
    }

    public List<ProductResponse> mapToProductListResponse(List<Product> productList){
        return productMapper.toResponses(productList);
    }

    public ProductResponse mapToProductResponse(Product product){
        return productMapper.toResponse(product);
    }

    public Product mapToProduct(CreateProductRequest createProductRequest){
        return productMapper.fromCreateRequest(createProductRequest);
    }


}
