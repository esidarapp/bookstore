package com.example.spring.service.order;

import com.example.spring.dto.order.OrderDto;
import com.example.spring.dto.order.OrderItemDto;
import com.example.spring.dto.order.OrderItemRequestDto;
import com.example.spring.dto.order.UpdateOrderStatusRequestDto;
import java.util.List;

public interface OrderService {
    OrderDto addOrder(Long userId, OrderItemRequestDto orderItemRequestDto);

    List<OrderDto> getOrders(Long userId);

    OrderDto getOrder(Long userId, Long orderId);

    OrderItemDto getOrderItem(Long userId, Long orderId, Long itemId);

    OrderDto updateOrderStatus(Long orderId,
                               UpdateOrderStatusRequestDto updateOrderItemRequestDto);
}
