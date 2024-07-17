package com.example.spring.controller;

import com.example.spring.dto.order.OrderDto;
import com.example.spring.dto.order.OrderItemDto;
import com.example.spring.dto.order.OrderItemRequestDto;
import com.example.spring.dto.order.UpdateOrderStatusRequestDto;
import com.example.spring.model.User;
import com.example.spring.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add a new order",
            description = "Creates a new order for the authenticated user.")
    @PostMapping
    public OrderDto addOrder(@RequestBody @Valid OrderItemRequestDto orderItemRequestDto,
                             Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.addOrder(user.getId(), orderItemRequestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order details",
            description = "Retrieves the details of a specific order for the authenticated user.")
    @GetMapping("/{orderId}/items")
    public OrderDto getOrder(@PathVariable Long orderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrder(user.getId(), orderId);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order item details",
            description = "Retrieves the details of a specific"
                    + " order item for the authenticated user.")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItem(@PathVariable Long orderId,
                                     @PathVariable Long itemId,
                                     Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItem(user.getId(), orderId, itemId);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all orders",
            description = "Retrieves a list of all orders for the authenticated user.")
    @GetMapping
    public List<OrderDto> getOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrders(user.getId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status",
            description = "Updates the status of a specific order."
                    + " Only accessible by ADMIN.")
    @PutMapping("/{id}")
    public OrderDto updateStatus(@PathVariable Long id,
                                 @RequestBody @Valid UpdateOrderStatusRequestDto requestDto) {
        return orderService.updateOrderStatus(id, requestDto);
    }
}
