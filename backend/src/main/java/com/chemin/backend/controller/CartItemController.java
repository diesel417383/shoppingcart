package com.chemin.backend.controller;

import com.chemin.backend.model.dto.CartItemResponse;
import com.chemin.backend.model.dto.AddProductToCartRequest;
import com.chemin.backend.model.vo.CartResponse;
import com.chemin.backend.model.entity.CartItem;
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
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        try{
            CartResponse cartResponse = cartService.getCart(userId);
            return ResponseEntity.ok(cartResponse);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    // 添加商品到購物車
    @PostMapping
    public ResponseEntity<CartItemResponse> addToCart(@RequestBody AddProductToCartRequest addProductToCartRequest) {
        CartItem cartItem = cartService.addToCart(addProductToCartRequest);
        return ResponseEntity.ok(cartService.mapToCartItemDto(cartItem));
    }

    // 更新數量
    @PutMapping("/{cartId}")
public ResponseEntity<CartItemResponse> updateQuantity(@PathVariable Long cartId, @RequestBody AddProductToCartRequest addProductToCartRequest) {
        CartItem cartItem = cartService.updateQuantity(cartId, addProductToCartRequest);
        CartItemResponse cartItemResponse = cartService.mapToCartItemDto(cartItem);
        return ResponseEntity.ok(cartItemResponse);
    }

    // 刪除商品
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
        return ResponseEntity.noContent().build(); // 204 no content

    }
}
