package com.chemin.backend.controller;

import com.chemin.backend.dto.CartItemDto;
import com.chemin.backend.dto.request.AddProductToCartRequest;
import com.chemin.backend.dto.response.CartItemResponse;
import com.chemin.backend.entity.CartItem;
import com.chemin.backend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartService;

    // 獲取購物車物品
    @GetMapping("/{userId}")
    public ResponseEntity<CartItemResponse> getCart(@PathVariable Long userId) {
        try{
            CartItemResponse cartItemResponse = cartService.getCart(userId);
            return ResponseEntity.ok(cartItemResponse);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    // 添加商品到購物車
    @PostMapping
    public ResponseEntity<CartItemDto> addToCart(@RequestBody AddProductToCartRequest addProductToCartRequest) {
        CartItem cartItem = cartService.addToCart(addProductToCartRequest);
        return ResponseEntity.ok(cartService.mapToCartItemDto(cartItem));
    }

    // 更新數量
    @PutMapping("/{cartId}")
public ResponseEntity<CartItemDto> updateQuantity(@PathVariable Long cartId, @RequestBody AddProductToCartRequest addProductToCartRequest) {
        CartItem cartItem = cartService.updateQuantity(cartId, addProductToCartRequest);
        CartItemDto cartItemDto = cartService.mapToCartItemDto(cartItem);
        return ResponseEntity.ok(cartItemDto);
    }

    // 刪除商品
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
        return ResponseEntity.noContent().build(); // 204 no content

    }
}
