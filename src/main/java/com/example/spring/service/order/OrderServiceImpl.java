package com.example.spring.service.order;

import com.example.spring.dto.order.OrderDto;
import com.example.spring.dto.order.OrderItemDto;
import com.example.spring.dto.order.OrderItemRequestDto;
import com.example.spring.dto.order.UpdateOrderStatusRequestDto;
import com.example.spring.exception.DataProcessingException;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.mapper.OrderItemMapper;
import com.example.spring.mapper.OrderMapper;
import com.example.spring.model.Order;
import com.example.spring.model.OrderItem;
import com.example.spring.model.ShoppingCart;
import com.example.spring.repository.order.OrderItemRepository;
import com.example.spring.repository.order.OrderRepository;
import com.example.spring.service.cart.CartService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final CartService cartService;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public OrderDto addOrder(Long userId, OrderItemRequestDto orderItemRequestDto) {
        ShoppingCart cart = cartService.findByUserId(userId);
        if (cart.getCartItems().isEmpty()) {
            throw new DataProcessingException(
                    String.format(
                            "Shopping cart for user with id %d is empty", userId));
        }
        Order order = orderMapper.cartToOrder(cart, orderItemRequestDto.shippingAddress());
        order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));
        orderRepository.save(order);
        cart.clearCart();
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrders(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<OrderItemDto> findOrderItemsByOrder(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(
                                "Order with id %d for user with id %d not found",
                                orderId, userId)));
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderItemDto getOrderItem(Long userId, Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository
                .findByIdAndOrderIdAndOrder_User_Id(itemId, orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Item with id %d not found in order with id %d for user with id %d",
                        itemId, orderId, userId)));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long orderId,
                                      UpdateOrderStatusRequestDto updateOrderItemRequestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order with id " + orderId + " not found"));
        order.setStatus(updateOrderItemRequestDto.status());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}
