package com.example.order.web;

import com.example.domain.ItemLine;

import java.util.List;

public class CreateOrderRequest {

    private Long customerId;

    private List<ItemLine> items;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<ItemLine> getItems() {
        return items;
    }

    public void setItems(List<ItemLine> items) {
        this.items = items;
    }
}
