package com.chemin.backend.Mapper;
import com.chemin.backend.dto.response.AddressResponse;
import com.chemin.backend.dto.response.OrderItemResponse;
import com.chemin.backend.dto.response.OrderResponse;
import com.chemin.backend.entity.Address;
import com.chemin.backend.entity.Order;
import com.chemin.backend.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface OrderMapper {

    @Mapping(source = "address", target = "address")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponses(List<Order> list);

    List<OrderItemResponse> orderItemListToOrderItemResponseList(List<OrderItem> list);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem);
}


