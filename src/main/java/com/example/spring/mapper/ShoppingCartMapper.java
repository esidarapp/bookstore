package com.example.spring.mapper;

import com.example.spring.config.MapperConfig;
import com.example.spring.dto.shoppingcart.ShoppingCartDto;
import com.example.spring.model.ShoppingCart;
import com.example.spring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring",
        uses = {CartItemMapper.class})
public interface ShoppingCartMapper {

    @Mapping(source = "user", target = "userId", qualifiedByName = "idFromUser")
    @Mapping(target = "cartItems", source = "cartItems") // Привязка cartItems
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Named("idFromUser")
    default Long idFromUser(User user) {
        return user != null ? user.getId() : null;
    }
}

