package com.example.spring.service.order;

import com.example.spring.dto.order.OrderDto;
import com.example.spring.dto.order.OrderItemDto;
import com.example.spring.dto.order.OrderItemRequestDto;
import com.example.spring.dto.order.UpdateOrderStatusRequestDto;
import com.example.spring.exception.DataProcessingException;
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
import com.example.spring.repository.user.UserRepository;
import com.example.spring.service.cart.CartService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
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
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, orderId)
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
                        "Order with id" + orderId + "not found"));
        order.setStatus(updateOrderItemRequestDto.status());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private void setUserForOrder(Order order, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with id " + userId + " not found"));;
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
        setUserForOrder(order, userId);
        setTotalPrice(order);
        return order;
    }
}
