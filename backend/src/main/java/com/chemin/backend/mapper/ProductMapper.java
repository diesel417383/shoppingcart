package com.chemin.backend.mapper;

import com.chemin.backend.model.dto.CreateProductRequest;
import com.chemin.backend.model.vo.ProductResponse;
import com.chemin.backend.model.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    Product fromCreateRequest(CreateProductRequest createProductRequest);

    List<ProductResponse> toResponses(List<Product> productList);
}
