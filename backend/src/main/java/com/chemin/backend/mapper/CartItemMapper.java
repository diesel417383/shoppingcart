package com.chemin.backend.mapper;

import com.chemin.backend.model.dto.CartItemResponse;
import com.chemin.backend.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

//    CartItemResponse mapToCartItemResponse(CartItem cartItem);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.description", target = "productDescription")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "product.images", target = "productImages")
    @Mapping(expression = "java(cartItem.getProduct().getPrice().doubleValue() * cartItem.getQuantity())", target = "totalPrice")
    CartItemResponse toCartItemDto(CartItem cartItem);

    List<CartItemResponse> toListCartItemDto(List<CartItem> cartItemList);
}
