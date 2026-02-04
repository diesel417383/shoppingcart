package com.chemin.backend.unit.service;

import com.chemin.backend.repository.OrderRepository;
import com.chemin.backend.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

//   練習寫service's findByUserId單元測試
//    @Test
//    void findByUserId_shouldReturnOrders_whenUserHasOrders(){
//        // Given
//        Long userId = 1L;
//
//        Order order1 = new Order();
//        order1.setId(101L);
//
//        Order order2 = new Order();
//        order2.setId(102L);
//
//        List<Order> orders = Arrays.asList(order1,order2);
//
//        when(orderRepository.findByUserId(userId).thenReturn(orders));
//
//        List<Order> result = orderService.findByUserId(userId);
//
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0).getId()).isEqualTo(101L);
//        assertThat(result.get(1).getId()).isEqualTo(102L);
//    }

    @Test
    void createOrderFromCart_shouldReturnOrder(){
        Long userId = 1L;

    }

}
