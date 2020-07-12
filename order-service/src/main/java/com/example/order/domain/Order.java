package com.example.order.domain;


import com.example.domain.OrderDto;
import com.example.utils.ConvertUtils;
import io.eventuate.tram.events.publisher.ResultWithEvents;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="orders")
@Access(AccessType.FIELD)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @Enumerated(EnumType.STRING)
    private RejectionReason rejectionReason;

    private Long customerId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<OrderDetails> orderDetails= new ArrayList<>();

    @Version
    private Long version;

    public Order() {
    }

    public static ResultWithEvents<Order> createOrder(OrderDto orderDto) {
        return new ResultWithEvents<>(makeOrder(orderDto), Collections.emptyList());
    }

    public Long getId() {
        return id;
    }

    public void noteProductReserved() {
        this.state = OrderState.APPROVED;
    }

    public void noteProductReservationFailed() {
        this.state = OrderState.REJECTED;
    }

    public OrderState getState() {
        return state;
    }

    public static Order makeOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setCustomerId(orderDto.getCustomerId());
        List<OrderDetails> orderDetails = orderDto.getItemLines().stream()
                .map(ConvertUtils::toOderDetails)
                .collect(Collectors.toList());
        orderDetails.stream().forEach(o -> o.setOrder(order));
        order.setOrderDetails(orderDetails);
        order.setCustomerId(orderDto.getCustomerId());
        order.setState(OrderState.PENDING);
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(RejectionReason rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
