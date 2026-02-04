package com.chemin.backend.service;

import com.chemin.backend.mapper.OrderMapper;
import com.chemin.backend.model.vo.OrderItemResponse;
import com.chemin.backend.model.vo.OrderResponse;
import com.chemin.backend.model.enums.OrderStatusEnum;
import com.chemin.backend.exception.ResourceNotFoundException;
import com.chemin.backend.model.entity.*;
import com.chemin.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderResponse findById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order ID not found"));
        return orderMapper.toResponse(order);

    }

    public List<OrderResponse> findByUserId(Long userId) {
        List<Order> list = orderRepository.findByUserId(userId);
        List<OrderResponse> resList = orderMapper.toResponses(list);
        return resList;

    }

    @Transactional
    public OrderResponse createOrderFromCart(Long userId, Long addressId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ID not found"));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address ID not found"));

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User ID not found"));

        try{
            if(cartItems.isEmpty()) throw new ResourceNotFoundException("Cart is empty");

            Order order = new Order();
            order.setOrderNo(OrderNoGenerator.generate());
            order.setUser(user);
            order.setAddress(address);
            order.setStatus(OrderStatusEnum.PENDING);

            BigDecimal totalAmount = BigDecimal.ZERO;
            List<OrderItem> orderItems = new ArrayList<>();
            for(CartItem cartItem: cartItems){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProduct().getPrice());

                orderItems.add(orderItem);
                totalAmount = totalAmount.add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            }
            order.setOrderItems(orderItems);
            order.setTotalAmount(totalAmount);

            Order savedOrder = orderRepository.save(order);
            OrderResponse orderResponse = orderMapper.toResponse(savedOrder);
            cartItemRepository.deleteAll(cartItems);

            return orderResponse;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderItemResponse> findOrderItemsByOrderId(Long orderId) {
        return orderMapper.orderItemListToOrderItemResponseList(orderItemRepository.findByOrderId(orderId));
    }

    @Transactional
    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    public class OrderNoGenerator {

        public static String generate() {
            String date = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            String random = UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase();

            return "ORD-" + date + "-" + random;
        }
    }

}
