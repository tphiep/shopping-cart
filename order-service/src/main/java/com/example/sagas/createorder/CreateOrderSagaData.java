package com.example.sagas.createorder;

import com.example.domain.OrderDto;
import com.example.order.domain.RejectionReason;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderSagaData {

    private long orderId;
    private OrderDto orderDto;
    private RejectionReason rejectionReason;

    public CreateOrderSagaData(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public CreateOrderSagaData() {}

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(RejectionReason rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
