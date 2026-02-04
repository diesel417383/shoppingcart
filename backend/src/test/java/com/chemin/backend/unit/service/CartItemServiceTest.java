package com.chemin.backend.unit.service;

import com.chemin.backend.model.dto.AddProductToCartRequest;
import com.chemin.backend.model.entity.CartItem;
import com.chemin.backend.model.entity.Product;
import com.chemin.backend.model.entity.User;
import com.chemin.backend.repository.CartItemRepository;
import com.chemin.backend.repository.ProductRepository;
import com.chemin.backend.service.CartItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemService cartService;

    @Test
    void addToCart_shouldAddProductToCart_whenValidRequest() {
        // Given
        AddProductToCartRequest dto = new AddProductToCartRequest();
        dto.setProductId(1L);
        dto.setQuantity(2);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Address");
        product.setStock(10);
        product.setPrice(BigDecimal.valueOf(100.0));

        User user = new User();
        user.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByUserAndProductId(any(User.class), eq(1L)))
                .thenReturn(Optional.empty());

        // When
        cartService.addToCart(dto);

        // Then - 驗證方法行為
        verify(productRepository).findById(1L);
        verify(cartItemRepository).findByUserAndProductId(any(User.class), eq(1L));

        // 使用 ArgumentCaptor 驗證儲存的 CartItem 狀態
        ArgumentCaptor<CartItem> captor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartItemRepository).save(captor.capture());

        CartItem savedItem = captor.getValue();

        // 狀態驗證
        assertEquals(2, savedItem.getQuantity(), "購物車數量應該等於請求數量");
        assertEquals(product, savedItem.getProduct(), "購物車商品應該是正確商品");
        assertNotNull(savedItem.getCreatedAt(), "新增時間不應該為 null");
    }

}
