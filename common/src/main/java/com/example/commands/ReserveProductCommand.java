package com.example.commands;

import com.example.domain.OrderDto;
import io.eventuate.tram.commands.common.Command;


public class ReserveProductCommand implements Command {
    private Long orderId;
    private OrderDto orderDto;

    public ReserveProductCommand() {}

    public ReserveProductCommand(Long orderId, OrderDto orderDto) {
        this.orderId = orderId;
        this.orderDto = orderDto;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }
}

