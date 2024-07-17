package com.example.spring.service.order;

import com.example.spring.dto.order.OrderDto;
import com.example.spring.dto.order.OrderItemDto;
import com.example.spring.dto.order.OrderItemRequestDto;
import com.example.spring.dto.order.UpdateOrderStatusRequestDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.mapper.OrderItemMapper;
import com.example.spring.mapper.OrderMapper;
import com.example.spring.model.CartItem;
import com.example.spring.model.Order;
import com.example.spring.model.OrderItem;
import com.example.spring.model.ShoppingCart;
import com.example.spring.model.User;
import com.example.spring.repository.order.OrderItemRepository;
import com.example.spring.repository.order.OrderRepository;
import com.example.spring.service.cart.CartService;
import com.example.spring.service.user.UserService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final CartService cartService;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public OrderDto addOrder(Long userId, OrderItemRequestDto orderItemRequestDto) {
        Order order = createOrder(userId, orderItemRequestDto);
        orderRepository.save(order);
        setOrderItems(order, userId);
        cartService.deleteCartItems(userId);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrders(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto getOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id %d not found", orderId)));
        if (!order.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException(String.format(
                    "Order with id %d not found for user with id %d", orderId, userId));
        }
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderItemDto getOrderItem(Long userId, Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Item with id %d not found in order with id %d", itemId, orderId)));
        if (!orderItem.getOrder().getUser().getId().equals(userId)) {
            throw new EntityNotFoundException(String.format(
                    "Order with id %d not found for user with id %d", orderId, userId));
        }
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long orderId,
                                      UpdateOrderStatusRequestDto updateOrderItemRequestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order with id" + orderId + "not found"));
        order.setStatus(updateOrderItemRequestDto.status());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private void setDate(Order order) {
        LocalDateTime localDateTime = LocalDateTime.now();
        order.setOrderDate(localDateTime);
    }

    private void setUserForOrder(Order order, Long userId) {
        User user = userService.findById(userId);
        order.setUser(user);
    }

    private void setOrderItems(Order order, Long userId) {
        ShoppingCart shoppingCart = cartService.findByUserId(userId);
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(orderItem.getBook().getPrice());
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
            order.addOrderItem(orderItem);
        }
    }

    private void setTotalPrice(Order order) {
        ShoppingCart shoppingCart = cartService.findByUserId(order.getUser().getId());
        BigDecimal totalPrice = shoppingCart.getCartItems().stream()
                .map(item -> item.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(totalPrice);
    }

    private Order createOrder(Long userId, OrderItemRequestDto orderItemRequestDto) {
        Order order = new Order();
        order.setShippingAddress(orderItemRequestDto.shippingAddress());
        setDate(order);
        setUserForOrder(order, userId);
        setTotalPrice(order);
        order.setStatus(Order.Status.PENDING);
        return order;
    }
}
