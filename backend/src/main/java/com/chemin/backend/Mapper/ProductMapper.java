package com.chemin.backend.Mapper;

import com.chemin.backend.dto.request.CreateProductRequest;
import com.chemin.backend.dto.response.ProductResponse;
import com.chemin.backend.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    Product fromCreateRequest(CreateProductRequest createProductRequest);

    List<ProductResponse> toResponses(List<Product> productList);
}
