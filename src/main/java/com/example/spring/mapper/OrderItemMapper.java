package com.example.spring.mapper;

import com.example.spring.config.MapperConfig;
import com.example.spring.dto.order.OrderItemDto;
import com.example.spring.model.CartItem;
import com.example.spring.model.OrderItem;
import java.math.BigDecimal;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price",source = "cartItem",qualifiedByName = "price")
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(CartItem cartItem);

    Set<OrderItem> toOrderItemSet(Set<CartItem> cartItemSet);

    @Named("price")
    default BigDecimal getPrice(CartItem cartItem) {
        BigDecimal pricePerBook = cartItem.getBook().getPrice();
        BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());
        return pricePerBook.multiply(quantity);
    }
}
