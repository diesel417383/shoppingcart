package com.chemin.backend.service;

import com.chemin.backend.Mapper.CartItemMapper;
import com.chemin.backend.dto.request.AddProductToCartRequest;
import com.chemin.backend.dto.response.CartItemResponse;
import com.chemin.backend.dto.CartItemDto;
import com.chemin.backend.entity.CartItem;
import com.chemin.backend.entity.Product;
import com.chemin.backend.entity.User;
import com.chemin.backend.exception.ResourceNotFoundException;
import com.chemin.backend.repository.CartItemRepository;
import com.chemin.backend.repository.ProductRepository;
import com.chemin.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Transactional
    public CartItem addToCart(AddProductToCartRequest dto) {

        Long userId = dto.getUserId();
        Long productId = dto.getProductId();
        Integer quantity = dto.getQuantity();

        // 查詢使用者ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 檢查商品ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product ID not found"));

        // 檢查庫存
        if (product.getStock() < quantity) {
            throw new RuntimeException("庫存不足");
        }

        // 檢查購物車中是否已有該商品
        Optional<CartItem> existingItem = cartItemRepository.findByUserAndProductId(user, productId);
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            // 再次檢查庫存
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("庫存不足");
            }
            return cartItemRepository.save(cartItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            return cartItemRepository.save(newItem);
        }
    }

    @Transactional
    public CartItemResponse getCart(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User ID not found"));

        List<CartItemDto> cartItemDtoList = cartItemMapper.toListCartItemDto(cartItems);

        Double totalPrice = cartItemDtoList.stream()
                .mapToDouble(CartItemDto::getTotalPrice)
                .sum();

        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setItems(cartItemDtoList);
        cartItemResponse.setTotalPrice(totalPrice);
        return cartItemResponse;
    }

    @Transactional
    public CartItem updateQuantity(Long cartId, AddProductToCartRequest addProductToCartRequest) {
        Integer quantity = addProductToCartRequest.getQuantity();
        CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart ID not found"));
        // 檢查庫存
        if (cartItem.getProduct().getStock() < quantity) {
            throw new RuntimeException("庫存不足");
        }

        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeFromCart(Long cartId) {
        CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart ID not found"));
        cartItemRepository.delete(cartItem);
    }

    public CartItemDto mapToCartItemDto(CartItem cartItem){
        return cartItemMapper.toCartItemDto(cartItem);
    }
}
