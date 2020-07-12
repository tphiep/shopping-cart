package com.example.order.domain;

import com.example.domain.OrderDto;

import java.util.Optional;

public interface OrderService {
    Optional<OrderDto> getOrder(long id);

    Optional<OrderDto> createOrder(final OrderDto orderDto);
}
